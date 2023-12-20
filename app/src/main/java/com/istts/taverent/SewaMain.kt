package com.istts.taverent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.istts.taverent.databinding.ActivitySewaMainBinding

class SewaMain : AppCompatActivity() {
    private lateinit var binding: ActivitySewaMainBinding
    private lateinit var pemilik: Pemilik
    var id_pemilik = ""
    var username=""
    var nama_pemilik = ""
    var WS_HOST = ""
    var pemiliks: ArrayList<Pemilik> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WS_HOST = resources.getString(R.string.WS_HOST)
        binding = ActivitySewaMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        id_pemilik = intent.getStringExtra("id_pemilik").toString()
        nama_pemilik = intent.getStringExtra("nama_pemilik").toString()
        username= intent.getStringExtra("username").toString()

        pemilik = intent.getParcelableExtra<Pemilik>("pemilik") as Pemilik
        Log.e("tag",pemilik.toString())



        val fragreplace=HomeSewa()
        val bundle = Bundle()
        bundle.putParcelable("pemilik",pemilik)
        bundle.putString("id_pemilik",id_pemilik)
        bundle.putString("nama_pemilik",nama_pemilik)
        bundle.putString("username",username)
        fragreplace.arguments = bundle
        val transaction = supportFragmentManager.beginTransaction().replace(
            R.id.frag4,fragreplace
        ).setReorderingAllowed(true).commit()

        binding.bottomNavSewa.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.btnHomeSewa -> {
                    val fragreplace = HomeSewa()
                    val bundle = Bundle()
                    bundle.putParcelable("pemilik", pemilik)
                    bundle.putString("id_pemilik", id_pemilik)
                    bundle.putString("nama_pemilik", nama_pemilik)
                    bundle.putString("username", username)
                    fragreplace.arguments = bundle
                    val transaction = supportFragmentManager.beginTransaction().replace(
                        R.id.frag4, fragreplace
                    ).setReorderingAllowed(true).commit()
                }
                R.id.btnChatSewa -> {
                    val fragreplace = ChatSewa()
                    val bundle = Bundle()
                    bundle.putParcelable("pemilik", pemilik)
                    bundle.putString("id_pemilik", id_pemilik)
                    bundle.putString("nama_pemilik", nama_pemilik)
                    bundle.putString("username", username)
                    fragreplace.arguments = bundle
                    val transaction = supportFragmentManager.beginTransaction().replace(
                        R.id.frag4, fragreplace
                    ).setReorderingAllowed(true).commit()
                }
                R.id.btnKelolaSewa -> {
                    val fragreplace = KelolaSewa()
                    val bundle = Bundle()
                    bundle.putParcelable("pemilik", pemilik)
                    bundle.putString("id_pemilik", id_pemilik)
                    bundle.putString("nama_pemilik", nama_pemilik)
                    bundle.putString("username", username)
                    fragreplace.arguments = bundle
                    val transaction = supportFragmentManager.beginTransaction().replace(
                        R.id.frag4, fragreplace
                    ).setReorderingAllowed(true).commit()
                }
//                R.id.btnStatistikSewa -> {
//                    val fragreplace = StatistikSewa()
//                    val bundle = Bundle()
//                    bundle.putParcelable("pemilik", pemilik)
//                    bundle.putString("id_pemilik", id_pemilik)
//                    bundle.putString("nama_pemilik", nama_pemilik)
//                    bundle.putString("username", username)
//                    fragreplace.arguments = bundle
//                    val transaction = supportFragmentManager.beginTransaction().replace(
//                        R.id.frag4, fragreplace
//                    ).setReorderingAllowed(true).commit()
//                }
                R.id.btnAkunSewa -> {
                    val fragreplace = AkunSewa()
                    val bundle = Bundle()
                    bundle.putParcelable("pemilik", pemilik)
                    bundle.putString("id_pemilik", id_pemilik)
                    bundle.putString("nama_pemilik", nama_pemilik)
                    bundle.putString("username", username)
                    fragreplace.arguments = bundle
                    val transaction = supportFragmentManager.beginTransaction().replace(
                        R.id.frag4, fragreplace
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