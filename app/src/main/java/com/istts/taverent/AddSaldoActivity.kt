package com.istts.taverent

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.istts.taverent.databinding.ActivityAddSaldoBinding
import com.midtrans.sdk.corekit.core.MidtransSDK
import com.midtrans.sdk.corekit.core.TransactionRequest
import com.midtrans.sdk.corekit.core.themes.CustomColorTheme
import com.midtrans.sdk.corekit.models.CustomerDetails
import com.midtrans.sdk.corekit.models.ItemDetails
import com.midtrans.sdk.corekit.models.snap.TransactionResult
import com.midtrans.sdk.uikit.SdkUIFlowBuilder


class AddSaldoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddSaldoBinding
    private lateinit var penginap: Penginap
    var WS_HOST = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddSaldoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        WS_HOST = resources.getString(R.string.WS_HOST)

        penginap = intent.getParcelableExtra<Penginap>("penginap") as Penginap
        SdkUIFlowBuilder.init()
            .setClientKey("SB-Mid-client-CAcpC230ptHYx8Z1") // client_key is mandatory
            .setContext(this) // context is mandatory
            .setTransactionFinishedCallback {
                    result: TransactionResult->
                if (result.response != null) {
                    when (result.status) {
                        TransactionResult.STATUS_SUCCESS -> {
                            val strReg = object : StringRequest(
                                Method.POST,"$WS_HOST/penginap/tambahsaldo",
                                Response.Listener {
                                    Toast.makeText(this,
                                        "Saldo berhasil ditambah",
                                        Toast.LENGTH_SHORT).show()
                                    val resultActivity = Intent()
                                    resultActivity.putExtra("penginap",penginap)
                                    setResult(Activity.RESULT_OK,resultActivity)
                                    finish()
                                },
                                Response.ErrorListener {
                                    Toast.makeText(this,
                                        it.message.toString(),
                                        Toast.LENGTH_SHORT).show()
                                }
                            ){
                                override fun getParams(): MutableMap<String, String>? {
                                    val params = HashMap<String, String>()
                                    params["id"] = penginap.id.toString()
                                    params["saldo"] = (penginap.saldo+binding.editHarga.text.toString().toInt()).toString()
                                    penginap.saldo = (penginap.saldo+binding.editHarga.text.toString().toInt())
                                    return params
                                }
                            }
                            val queue: RequestQueue = Volley.newRequestQueue(this)
                            queue.add(strReg)
                        }
                        TransactionResult.STATUS_PENDING -> Toast.makeText(this,
                            "Transaction Pending. ID: " + result.response.transactionId,
                            Toast.LENGTH_LONG).show()
                        TransactionResult.STATUS_FAILED -> Toast.makeText(this,
                            "Transaction Failed. ID: " + result.response.transactionId.toString() + ". Message: " + result.response.statusMessage,
                            Toast.LENGTH_LONG).show()
                    }
                    result.response.validationMessages
                } else if (result.isTransactionCanceled) {
                    Toast.makeText(this, "Transaction Canceled", Toast.LENGTH_LONG).show()
                } else {
                    if (result.status.equals(TransactionResult.STATUS_INVALID,true)) {
                        Toast.makeText(this, "Transaction Invalid", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this,
                            "Transaction Finished with failure.",
                            Toast.LENGTH_LONG).show()
                    }
                }
            } // set transaction finish callback (sdk callback)
            .setMerchantBaseUrl("https://tavarent123.000webhostapp.com/index.php/") //set merchant url (required)
            .enableLog(true) // enable sdk log (optional)
            .setColorTheme(CustomColorTheme("#FFE51255",
                "#B61548",
                "#FFE51255")) // set theme. it will replace theme on snap theme on MAP ( optional)
            .setLanguage("id") //`en` for English and `id` for Bahasa
            .buildSDK()

        binding.button17.setOnClickListener {
            if (binding.editHarga.text.toString()!=""){
                if (binding.editHarga.text.toString().toInt()>0){
                    val transactionRequest = TransactionRequest("ISIULANG"+System.currentTimeMillis().toString()+"", binding.editHarga.text.toString().toDouble())
                    val customerDetails = CustomerDetails()
                    customerDetails.customerIdentifier = penginap.id.toString()
                    customerDetails.phone = penginap.no_telp
                    customerDetails.firstName =
                        penginap.nama_lengkap.substring(0,penginap.nama_lengkap.indexOf(" ",0))
                    customerDetails.lastName =
                        penginap.nama_lengkap.substring(penginap.nama_lengkap.indexOf(" ",0),penginap.nama_lengkap.length-1)
                    customerDetails.email = penginap.email
                    transactionRequest.customerDetails = customerDetails;
                    val itemDetails1 =
                        ItemDetails("1", binding.editHarga.text.toString().toDouble(), 1, "Isi ulang")

                    val itemDetailsList: ArrayList<ItemDetails> = ArrayList()
                    itemDetailsList.add(itemDetails1)

                    transactionRequest.itemDetails = itemDetailsList

                    MidtransSDK.getInstance().transactionRequest = transactionRequest
                    MidtransSDK.getInstance().startPaymentUiFlow(this@AddSaldoActivity)

                }
            }
        }
    }
}