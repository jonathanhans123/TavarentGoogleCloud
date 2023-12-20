package com.istts.taverent

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.istts.taverent.CurrencyUtils.toRupiah


class HomeSewa : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_sewa, container, false)
    }
    private lateinit var pemilik: Pemilik
    var id_pemilik = ""
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        id_pemilik = arguments?.getString("id_pemilik").toString()
        val nama_pemilik = arguments?.getString("nama_pemilik").toString()
        val username = arguments?.getString("username").toString()
        pemilik = arguments?.getParcelable<Pemilik>("pemilik") as Pemilik

        val btnNotifikasi = view.findViewById<ImageView>(R.id.imageView18)
        val txOwnerUsername1 = view.findViewById<TextView>(R.id.txOwnerUsername1)
        val txOwnerUsername2 = view.findViewById<TextView>(R.id.txOwnerUsername2)
        val tvSaldo = view.findViewById<TextView>(R.id.txsaldoSewa)
        val btnpindah = view.findViewById<ImageView>(R.id.btnTambahKosApartmen)
        var Temp =""
        tvSaldo.text = pemilik.saldo.toRupiah()
        txOwnerUsername1.setText(nama_pemilik)
        txOwnerUsername2.setText(username)
        btnpindah.setOnClickListener {
            val intent = Intent(view.context,TambahProperti::class.java)
            intent.putExtra("id_pemilik",id_pemilik)
            activity?.runOnUiThread { byResult.launch(intent) }
        }
        btnNotifikasi.setOnClickListener {
            val intent = Intent(view.context, NotificationActivity::class.java)
            intent.putExtra("tipe",1)
            startActivity(intent)
        }
    }
    val byResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK){
            refreshPenginapan()
        }
    }

    fun refreshPenginapan(){

    }

}