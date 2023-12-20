package com.istts.taverent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.istts.taverent.databinding.ActivityAddAnnounceBinding

class AddAnnounceActivity : AppCompatActivity() {

    var WS_HOST = ""
    private lateinit var binding: ActivityAddAnnounceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAnnounceBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        WS_HOST=resources.getString(R.string.WS_HOST)

        binding.radioButton5.isChecked = true
        binding.button7.setOnClickListener {
            val judul = binding.editTextTextPersonName2.text.toString()
            val isi = binding.editTextTextPersonName3.text.toString()
            var tipe = 0
            if (binding.radioButton5.isChecked){
                tipe = 0
            }else{
                tipe = 1
            }
            if (judul!=""&&isi!=""){
                insertPengumuman(judul,isi, tipe)
            }else{
                Toast.makeText(this@AddAnnounceActivity, "Fill all field", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun insertPengumuman(judul:String,isi:String,tipe:Int){
        val strReg = object : StringRequest(
            Method.POST,"$WS_HOST/pengumuman/insert",
            Response.Listener {
                Toast.makeText(this@AddAnnounceActivity, "Pengumuman created", Toast.LENGTH_SHORT).show()
                finish()
            },
            Response.ErrorListener {
                Log.e("TRACE",it.stackTraceToString())
            }
        ){
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                params["judul"] = judul
                params["isi"] = isi
                params["tipe"] = tipe.toString()
                return params
            }
        }
        val queue: RequestQueue = Volley.newRequestQueue(this@AddAnnounceActivity)
        queue.add(strReg)

    }
}