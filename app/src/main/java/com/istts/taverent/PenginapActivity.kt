package com.istts.taverent

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.istts.taverent.CurrencyUtils.toRupiah
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.istts.taverent.databinding.ActivityPenginapBinding
import org.json.JSONObject


class PenginapActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPenginapBinding
    private lateinit var penginap: Penginap
    var WS_HOST = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPenginapBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        WS_HOST = resources.getString(R.string.WS_HOST)


        penginap = intent.getParcelableExtra<Penginap>("penginap") as Penginap
        val fragment = PenginapCariFragment()
        val bundle = Bundle()
        bundle.putParcelable("penginap",penginap)
        val id_penginap = intent.getStringExtra("id_penginap").toString()
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction().replace(
            R.id.frag3,fragment
        ).setReorderingAllowed(true).commit()

        binding.bottomNavPenginap.setOnItemSelectedListener {
            when(it.itemId){
                R.id.searchitem->{
                    refreshPenginap()
                    val fragment = PenginapCariFragment()
                    val bundle = Bundle()
                    bundle.putParcelable("penginap",penginap)
                    fragment.arguments = bundle
                    supportFragmentManager.beginTransaction().replace(
                        R.id.frag3,fragment
                    ).setReorderingAllowed(true).commit()
                }
                R.id.favorititem->{
                    val fragment = PenginapFavoritFragment()
                    val bundle = Bundle()
                    bundle.putParcelable("penginap",penginap)
                    fragment.arguments = bundle
                    supportFragmentManager.beginTransaction().replace(
                        R.id.frag3,fragment
                    ).setReorderingAllowed(true).commit()
                }
                R.id.homeitem->{
                    val fragment = PenginapHomeFragment()
                    val bundle = Bundle()
                    bundle.putParcelable("penginap",penginap)
                    fragment.arguments = bundle
                    supportFragmentManager.beginTransaction().replace(
                        R.id.frag3,fragment
                    ).setReorderingAllowed(true).commit()
                }
                R.id.chatitem->{
                    val fragment = PenginapChatFragment()
                    val bundle = Bundle()
                    bundle.putParcelable("penginap",penginap)
                    fragment.arguments = bundle
                    supportFragmentManager.beginTransaction().replace(
                        R.id.frag3,fragment
                    ).setReorderingAllowed(true).commit()
                }
                R.id.accountitem->{
                    val fragment = PenginapAccountFragment()
                    val bundle = Bundle()
                    bundle.putParcelable("penginap",penginap)
                    bundle.putString("id_penginap",id_penginap)
                    fragment.arguments = bundle
                    supportFragmentManager.beginTransaction().replace(
                        R.id.frag3,fragment
                    ).setReorderingAllowed(true).commit()
                }
            }
            return@setOnItemSelectedListener true
        }
    }
    fun refreshPenginap(){
        val strReq = object : StringRequest(
            Method.POST,"$WS_HOST/penginap/find",
            Response.Listener {
                val o = JSONObject(it)
                val id = o.getInt("id")
                val username = o.getString("username")
                val password = o.getString("password")
                val nama_lengkap = o.getString("nama_lengkap")
                val email = o.getString("email")
                val no_telp = o.getString("no_telp")
                val saldo = o.getInt("saldo")
                var deleted_at = ""
                if (o.has("deleted_at")) {
                    deleted_at = o.getString("deleted_at")
                }
                Log.e("saldo",saldo.toRupiah())

                penginap.saldo = saldo

            },
            Response.ErrorListener {
                Toast.makeText(this, "WS_ERROR2", Toast.LENGTH_SHORT).show()
            }
        ){
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                params["id_penginap"] = penginap.id.toString()
                return params
            }
        }
        val queue: RequestQueue = Volley.newRequestQueue(this)
        queue.add(strReq)
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