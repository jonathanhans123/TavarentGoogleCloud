package com.istts.taverent

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.istts.taverent.databinding.FragmentPenginapChatBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray

class PenginapChatFragment : Fragment() {
    private lateinit var binding: FragmentPenginapChatBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPenginapChatBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }
    private lateinit var db: AppDatabase
    private lateinit var Chats: MutableList<ChatEntity>
    private val coroutine = CoroutineScope(Dispatchers.IO)
    var WS_HOST = ""
    private lateinit var rvChatPenginap: RVChatPenginap
    var chats:ArrayList<Chat> = ArrayList()
    var chatsLatest:ArrayList<Chat> = ArrayList()
    private lateinit var penginap: Penginap

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        WS_HOST = resources.getString(R.string.WS_HOST)
        db = AppDatabase.build(context)
        Chats = mutableListOf()
        penginap = arguments?.getParcelable<Penginap>("penginap") as Penginap

        rvChatPenginap = RVChatPenginap(chatsLatest,"penginap"){view: View, idx: Int ->
            val intent = Intent(view.context,ChatActivity::class.java)
            intent.putExtra("tipe","penginap")
            intent.putExtra("penginap",chats[idx].id_penginap)
            intent.putExtra("pemilik",chats[idx].id_pemilik)
            byResult.launch(intent)
        }

        if (checkForInternet(requireContext())) {

        } else {
            coroutine.launch {
                Chats.clear()
                Chats.addAll(db.userDao.fetchChat().toMutableList())
                Log.i("USER", Chats.toString())

                activity?.runOnUiThread(Runnable {
                    for (i in 0 until Chats.size) {
                        val id = Chats[i].id
                        val pesan = Chats[i].pesan
                        val id_penginap = Chats[i].id_penginap
                        val username_penginap = Chats[i].username_penginap
                        val id_pemilik = Chats[i].id_pemilik
                        var username_pemilik = Chats[i].username_pemilik
                        var created_at = Chats[i].created_at
                        var status = Chats[i].status
                        var sender = Chats[i].sender
                        val p = Chat(id,pesan,id_penginap,username_penginap,id_pemilik,username_pemilik,created_at,status,sender)
                        chats.add(p)
                        rvChatPenginap.notifyDataSetChanged()
                    }
                    findlatest()
                })
            }
        }
        refreshChat(view,penginap.id)

        binding.rvChatPenginap.adapter = rvChatPenginap
        binding.rvChatPenginap.layoutManager = LinearLayoutManager(view.context,LinearLayoutManager.VERTICAL,false)

    }
    fun findlatest(){
        for(i in chats.size-1 downTo 0){
            var exist = false
            for (j in 0 until chatsLatest.size) {
                Log.e("tag",i.toString()+j.toString())
                if (chatsLatest.size!=0) {
                    if (chats[i].id_penginap == chatsLatest[j].id_penginap &&
                        chats[i].id_pemilik == chatsLatest[j].id_pemilik
                    ) {
                        exist = true
                    }
                }
            }
            if (!exist) {
                chatsLatest.add(chats[i])
            }
        }
    }

    fun refreshChat(view:View,id_penginap:Int) {
        val strReq = object : StringRequest(
            Method.POST, "$WS_HOST/chat/list/penginap",
            Response.Listener {
                val obj: JSONArray = JSONArray(it)
                chats.clear()
                coroutine.launch {
                    db.userDao.deleteChatTable()
                }
                for (i in 0 until obj.length()) {
                    val o = obj.getJSONObject(i)
                    val id = o.getInt("id")
                    val pesan = o.getString("pesan")
                    val id_penginap = o.getInt("id_penginap")
                    val username_penginap = o.getString("penginapusername")
                    val id_pemilik = o.getInt("id_pemilik")
                    var username_pemilik = o.getString("pemilikusername")
                    var created_at = o.getString("created_at")
                    var status = o.getString("status")
                    var sender = o.getString("sender")
                    val p = Chat(id,
                        pesan,
                        id_penginap,
                        username_penginap,
                        id_pemilik,
                        username_pemilik,
                        created_at,
                        status,
                        sender
                    )
                    val c = ChatEntity(
                        id = id,
                        pesan = pesan,
                        id_penginap = id_penginap,
                        username_penginap = username_penginap,
                        id_pemilik = id_pemilik,
                        username_pemilik = username_pemilik,
                        created_at = created_at,
                        status = status,
                        sender = sender,
                    )
                    coroutine.launch {
                        db.userDao.insert(c)
                    }
                    chats.add(p)
                    rvChatPenginap.notifyDataSetChanged()
                }
                findlatest()
            },
            Response.ErrorListener {
                Toast.makeText(view.context, "WS_ERROR1", Toast.LENGTH_SHORT).show()
            }
        ) {
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                params["id_penginap"] = id_penginap.toString()
                return params
            }
        }
        val queue: RequestQueue = Volley.newRequestQueue(view.context)
        queue.add(strReq)
    }

    private fun checkForInternet(context: Context): Boolean {

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }

    val byResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        rvChatPenginap.notifyDataSetChanged()
    }
}