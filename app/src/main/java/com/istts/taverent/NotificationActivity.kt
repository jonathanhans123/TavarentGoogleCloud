package com.istts.taverent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.istts.taverent.databinding.ActivityNotificationBinding
import org.json.JSONArray

class NotificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotificationBinding
    private lateinit var judul: ArrayList<String>
    private lateinit var isi: ArrayList<String>
    private lateinit var rvNotification: RVPengumuman

    var WS_HOST = ""

    var tipe = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WS_HOST = resources.getString(R.string.WS_HOST)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        tipe = intent.getIntExtra("tipe",0)
        judul = arrayListOf()
        isi = arrayListOf()
        rvNotification = RVPengumuman(judul,isi)
        binding.rvNotification.adapter = rvNotification
        binding.rvNotification.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)

        refreshNotification()
    }

    fun refreshNotification(){
        val strReq = object : StringRequest(
            Method.GET, "$WS_HOST/pengumuman/list",
            Response.Listener {
                val obj: JSONArray = JSONArray(it)
                judul.clear()
                isi.clear()
                for (i in 0 until obj.length()) {
                    var o = obj.getJSONObject(i)
                    val j = o.getString("judul")
                    val i = o.getString("isi")
                    if (o.getInt("tipe") == tipe) {
                        judul.add(j)
                        isi.add(i)
                    }
                    Log.e("judul",j)
                    Log.e("isi",i)
                }
                rvNotification.notifyDataSetChanged()
            },
            Response.ErrorListener {
                Toast.makeText(this, "WS_ERROR1", Toast.LENGTH_SHORT).show()
            }
        ) {}
        val queue: RequestQueue = Volley.newRequestQueue(this)
        queue.add(strReq)
    }
}