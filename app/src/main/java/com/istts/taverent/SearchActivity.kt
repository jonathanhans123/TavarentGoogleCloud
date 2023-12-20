package com.istts.taverent

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.istts.taverent.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    var alamat = ""
    var koordinat = ""
    private lateinit var penginap: Penginap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        penginap = intent.getParcelableExtra<Penginap>("penginap")  as Penginap


        binding.button10.setOnClickListener {
            val intent = Intent(this@SearchActivity,MapActivity::class.java)
            byResult.launch(intent)
        }
        binding.imageButton7.setOnClickListener {
            finish()
        }
    }
    val byResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result: ActivityResult ->
        if (result.resultCode==Activity.RESULT_OK){
            val data = result.data
            if (data!=null){
                alamat = data.getStringExtra("alamat").toString()
                koordinat = data.getStringExtra("koordinat").toString()

                val intent = Intent(this@SearchActivity,SearchResultActivity::class.java)
                intent.putExtra("alamat",alamat)
                intent.putExtra("penginap",penginap)
                intent.putExtra("koordinat",koordinat)
                startActivity(intent)
            }
        }
    }
}