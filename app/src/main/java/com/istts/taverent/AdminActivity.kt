package com.istts.taverent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.istts.taverent.databinding.ActivityAdminBinding
import org.json.JSONArray
import java.util.ArrayList

class AdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminBinding
    var WS_HOST = ""
    var rented: ArrayList<Pembayaran2> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        WS_HOST = resources.getString(R.string.WS_HOST)



        val fragment = AdminHomeFragment()
        supportFragmentManager.beginTransaction().replace(
            R.id.frag2,fragment
        ).setReorderingAllowed(true).commit()
        binding.bottomNavAdmin.setOnItemSelectedListener {
            when (it.itemId){
                R.id.searchitem->{
                    val fragment = AdminHomeFragment()
                    supportFragmentManager.beginTransaction().replace(
                        R.id.frag2,fragment
                    ).setReorderingAllowed(true).commit()
                }
                R.id.listitem->{
                    val fragment = AdminListFragment()
                    supportFragmentManager.beginTransaction().replace(
                        R.id.frag2,fragment
                    ).setReorderingAllowed(true).commit()
                }
                R.id.gameitem->{
                    val strReq = object : StringRequest(
                        Method.GET,"$WS_HOST/pembayaran/list/semua",
                        Response.Listener {
                            val obj: JSONArray = JSONArray(it)
                            rented.clear()
                            for (i in 0 until obj.length()){
                                val o = obj.getJSONObject(i)
                                val id = o.getInt("id")
                                val total = o.getInt("total")
                                val tanggal_mulai = o.getString("tanggal_mulai")
                                val tanggal_selesai = o.getString("tanggal_selesai")
                                val id_penginap = o.getInt("id_penginap")
                                val id_penginapan = o.getInt("id_penginapan")
                                var nama_penginapan = ""
                                val p = Pembayaran2(id,total,tanggal_mulai,tanggal_selesai,id_penginap,id_penginapan,null,null,nama_penginapan)
                                rented.add(p)
                            }

                            val fragment = AdminGameFragment()
                            val bundle = Bundle()
                            bundle.putParcelableArrayList("rent",rented)
                            fragment.arguments = bundle
                            supportFragmentManager.beginTransaction().replace(
                                R.id.frag2,fragment
                            ).setReorderingAllowed(true).commit()

                        },
                        Response.ErrorListener {
                            Toast.makeText(this, "no network connection", Toast.LENGTH_SHORT).show()
                        }
                    ){}
                    val queue: RequestQueue = Volley.newRequestQueue(view.context)
                    queue.add(strReq)

                }
                R.id.notifitem->{
                    val fragment = AdminAnnounceFragment()
                    supportFragmentManager.beginTransaction().replace(
                        R.id.frag2,fragment
                    ).setReorderingAllowed(true).commit()
                }
                R.id.adminitem->{
                    val fragment = AdminAccountFragment()
                    supportFragmentManager.beginTransaction().replace(
                        R.id.frag2,fragment
                    ).setReorderingAllowed(true).commit()
                }
            }
            return@setOnItemSelectedListener true
        }
    }
    override fun onBackPressed() {
        val eBuilder = AlertDialog.Builder(this)
        eBuilder.setTitle("Exit")
        eBuilder.setIcon(R.drawable.ic_baseline_warning_24)
        eBuilder.setMessage("Are you sure you want to Exit ?, Press back again to abort")
        eBuilder.setPositiveButton("Yes"){
                Dialog, whichButton ->
            this.finishAffinity();
        }.show()
    }


}