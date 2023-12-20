package com.istts.taverent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.istts.taverent.CurrencyUtils.toRupiah
import com.istts.taverent.databinding.ActivityPembayaranBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class PembayaranActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPembayaranBinding
    private lateinit var penginap: Penginap
    private lateinit var penginapan: Penginapan
    var WS_HOST = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPembayaranBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        WS_HOST = resources.getString(R.string.WS_HOST)

        penginap = intent.getParcelableExtra<Penginap>("penginap") as Penginap
        penginapan = intent.getParcelableExtra<Penginapan>("penginapan") as Penginapan

        binding.tvNamaPenginapan.text = penginapan.nama
        binding.tvAlamatPenginapan.text = penginapan.alamat
        binding.tvJenisPenginapan.text = penginapan.jk_boleh
        binding.tvTipePenginapan.text = penginapan.tipe
        binding.tvSaldo.text = penginap.saldo.toRupiah()

        binding.tvHargaPerBulan.text = penginapan.harga.toRupiah()
        binding.tvJangkaWaktu.text = "0"
        binding.tvTotal.text = 0.toRupiah()
        binding.editTextNumber4.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.tvJangkaWaktu.text = binding.editTextNumber4.text.toString()
                binding.tvTotal.text = calcalatePrice().toRupiah()
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

        binding.button15.setOnClickListener {
            Log.e("tag",
                "harga"+(penginapan.harga*binding.tvJangkaWaktu.text.toString().toInt()).toString()+
                        "tanggal_mulai" + java.sql.Date(Date().time).toString() +
                        "tanggal_selsai" + java.sql.Date(Date().time.plus(60*60*24*30*binding.tvJangkaWaktu.text.toString().toInt())).toString() +
                        "id" + penginap.id + penginapan.id
            )
            if (binding.editTextNumber4.text.toString().toInt()>0) {
                if (penginapan.harga*binding.tvJangkaWaktu.text.toString().toInt()<=penginap.saldo) {
                    val strReg = object : StringRequest(
                        Method.POST, "$WS_HOST/pembayaran/insert",
                        Response.Listener {
                            Toast.makeText(this, "Berhasil melakukan transaksi", Toast.LENGTH_SHORT)
                                .show()
                            refreshPenginap(view,penginap.saldo-penginapan.harga*binding.tvJangkaWaktu.text.toString().toInt())
                            finish()
                        },
                        Response.ErrorListener {
                            Log.e("TRACE", it.stackTraceToString())
                        }
                    ) {
                        override fun getParams(): MutableMap<String, String>? {
                            val params = HashMap<String, String>()
                            val calendar1: Calendar = Calendar.getInstance()
                            val calendar2: Calendar = Calendar.getInstance()
                            calendar1.time =
                                SimpleDateFormat("yyyy-MM-dd").parse(java.sql.Date(Date().time)
                                    .toString())
                            calendar2.time =
                                SimpleDateFormat("yyyy-MM-dd").parse(java.sql.Date(Date().time)
                                    .toString())
                            calendar2.add(Calendar.DATE,
                                30 * binding.tvJangkaWaktu.text.toString().toInt())

                            params["total"] =
                                (penginapan.harga * binding.tvJangkaWaktu.text.toString()
                                    .toInt()).toString()
                            params["tanggal_mulai"] =
                                SimpleDateFormat("yyyy-MM-dd").format(calendar1.time)
                            params["tanggal_selesai"] =
                                SimpleDateFormat("yyyy-MM-dd").format(calendar2.time)
                            params["id_penginap"] = penginap.id.toString()
                            params["id_penginapan"] = penginapan.id.toString()
                            return params
                        }
                    }
                    val queue: RequestQueue = Volley.newRequestQueue(this)
                    queue.add(strReg)
                }else{
                    Toast.makeText(this, "Saldo anda tidak cukup", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "Jangka Waktu harus lebih dari 0", Toast.LENGTH_SHORT).show()
            }
        }


    }
    fun refreshPenginap(view: View,saldoakhir:Int){
        val strReq = object : StringRequest(
            Method.POST,"$WS_HOST/penginap/tambahsaldo",
            Response.Listener {
                finish()
            },
            Response.ErrorListener {
                Toast.makeText(view.context, "WS_ERROR2", Toast.LENGTH_SHORT).show()
            }
        ){
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                params["id_penginap"] = penginap.id.toString()
                params["saldo"] = saldoakhir.toString()
                return params
            }
        }
        val queue: RequestQueue = Volley.newRequestQueue(view.context)
        queue.add(strReq)
    }
    fun calcalatePrice(): Int{
        var original = penginapan.harga
        if (binding.editTextNumber4.text.toString()!="") {
            original *= binding.editTextNumber4.text.toString().toInt()
        }

        return original
    }
}