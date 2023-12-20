package com.istts.taverent

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.istts.taverent.databinding.FragmentPenginapAccountBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PenginapAccountFragment : Fragment() {
    private lateinit var binding: FragmentPenginapAccountBinding
    private lateinit var db: AppDatabase
    private val coroutine = CoroutineScope(Dispatchers.IO)
    var WS_HOST = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPenginapAccountBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }
    var id_penginap = ""
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        db = AppDatabase.build(context)
        super.onViewCreated(view, savedInstanceState)
        var nama_pemilik = ""
        id_penginap = arguments?.getString("id_penginap").toString()
        val btneditprofilpenginap = view.findViewById<LinearLayout>(R.id.btneditprofilpenginap)
        btneditprofilpenginap.setOnClickListener {
            val intent = Intent(view.context,PenginapProfile::class.java)
            intent.putExtra("id_penginap",id_penginap)
            activity?.runOnUiThread { startActivity(intent) }
        }
        binding.imsetting.setOnClickListener {
            WS_HOST = resources.getString(R.string.WS_HOST)
            val strReq = object : StringRequest(
                Method.GET,"$WS_HOST/pemilik/list",
                Response.Listener {
                    coroutine.launch {
                        db.userDao.deleteUserTable()
                        db.userDao.deleteChatTable()
                        db.userDao.deleteLGuestTable()
                    }
                    val intent = Intent(view.context, LoginActivity::class.java)
                    activity?.runOnUiThread { startActivity(intent) }
                },
                Response.ErrorListener {
                    Toast.makeText(context, "Check your internet connection", Toast.LENGTH_SHORT).show()
                }
            ){}
            val queue: RequestQueue = Volley.newRequestQueue(view.context)
            queue.add(strReq)
        }
    }
}