package com.istts.taverent

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.istts.taverent.databinding.ActivityTambahPropertiBinding

class TambahProperti : AppCompatActivity() {
    var WS_HOST = ""

    private lateinit var binding: ActivityTambahPropertiBinding
    private lateinit var buttons: ArrayList<TextView>
    private lateinit var check: ArrayList<Boolean>
    var koordinat = ""
    var id_pemilik = "1"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahPropertiBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        WS_HOST=resources.getString(R.string.WS_HOST)

        if (intent.hasExtra("id_pemilik")) {
            id_pemilik = intent.getStringExtra("id_pemilik").toString()
        }

        buttons = arrayListOf(
            binding.btnF1,
            binding.btnF2,
            binding.btnF3,
            binding.btnF4,
            binding.btnF5,
            binding.btnF6,
            binding.btnF7,
            binding.btnF8,
            binding.btnF9,
            binding.btnF10,
            binding.btnF11,
            binding.btnF12,
        )
        check = arrayListOf(
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
        )


        for(i in 0 until buttons.size){
            buttons[i].setOnClickListener {
                if (!check[i]){
                    check[i] = true
                    buttons[i].background = resources.getDrawable(R.drawable.rectangle_green)
                }else {
                    check[i] = false
                    buttons[i].background = resources.getDrawable(R.drawable.custom_edit_text2)
                }
            }
        }

        binding.button11.setOnClickListener {
            val intent = Intent(this@TambahProperti,MapActivity::class.java)
            byResult.launch(intent)
        }
        binding.btnbackSewa.setOnClickListener{
            finish()
        }
        binding.btnTambahProperti.setOnClickListener {
            val nama = binding.editNamaProperti.text.toString()
            val deskripsi = binding.editDeskripsi.text.toString()
            val alamat = binding.editAlamat.text.toString()
            val harga = binding.editHarga.text.toString()
            val jeniskelasmin = binding.spinner2.selectedItem.toString()
            var tipe = ""
            if (binding.rbKos.isChecked){
                tipe = binding.rbKos.text.toString()
            }else if(binding.rbApartemen.isChecked){
                tipe = binding.rbApartemen.text.toString()
            }

            var fasilitas = ""
            var counter = 0
            for(i in 0 until buttons.size){
                if (check[i]){
                    fasilitas += buttons[i].text.toString()+","
                    counter++
                }
            }

            if (nama!=""&&
                deskripsi!=""&&
                alamat!=""&&
                harga!=""&&
                tipe!=""&&
                fasilitas!=""&&
                counter>2&&
                id_pemilik!=""){
//                fasilitas = fasilitas.substring(0,fasilitas.length-1)
                val strReg = object : StringRequest(
                    Method.POST, "$WS_HOST/penginapan/insert",
                    Response.Listener {
                        Toast.makeText(this@TambahProperti,
                            "Properti Berhasil Ditambah", Toast.LENGTH_SHORT)
                            .show()
                        val resultIntent = Intent()
                        setResult(Activity.RESULT_OK,resultIntent)
                        finish()
                    },
                    Response.ErrorListener {
                        Toast.makeText(this, "error",
                            Toast.LENGTH_SHORT).show()
                    }
                ) {
                    override fun getParams(): MutableMap<String, String>? {
                        val params = HashMap<String, String>()
                        params["nama"] = nama.toString()
                        params["alamat"] = alamat.toString()
                        params["deskripsi"] = deskripsi.toString()
                        params["fasilitas"] = fasilitas.toString()
                        params["jk_boleh"] = jeniskelasmin.toString()
                        params["tipe"] = tipe.toString()
                        params["koordinat"] = koordinat.toString()
                        params["harga"] = harga.toString()
                        params["id_pemilik"] = id_pemilik.toString()
                        return params
                    }
                }
                val queue: RequestQueue = Volley.newRequestQueue(this)
                queue.add(strReg)
            }else{
                Toast.makeText(this@TambahProperti,
                    "Tolong isi semua field", Toast.LENGTH_SHORT).show()
            }

        }
    }
    val byResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK){
            val data = result.data
            if (data!=null){
                val alamat = data.getStringExtra("alamat").toString()
                Toast.makeText(this@TambahProperti, alamat.toString(), Toast.LENGTH_SHORT).show()
                binding.editAlamat.setText(alamat)
                koordinat = data.getStringExtra("koordinat").toString()
            }
        }
    }
}