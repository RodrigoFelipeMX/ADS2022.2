package com.ads.app.exchangeconvert

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.ads.app.exchangeconvert.api.endpoint
import com.ads.app.exchangeconvert.util.UtilsNetwork
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var spFrom : Spinner
    private lateinit var spTo : Spinner
    private lateinit var btConverter: Button
    private lateinit var tvResultado: TextView
    private lateinit var etValueFrom: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spFrom = findViewById(R.id.spFrom)
        spTo = findViewById(R.id.spTo)
        btConverter=findViewById(R.id.btConverter)
        this.tvResultado =findViewById(R.id.tvResultado)
        etValueFrom=findViewById(R.id.etValueFrom)

        getExchengers()

        btConverter.setOnClickListener { converterMoeda() }
    }
    fun converterMoeda(){
        val retrofitClient = UtilsNetwork.getRetrofitInstance("https://cdn.jsdelivr.net/")
        val endpoint=retrofitClient.create(endpoint::class.java)

        endpoint.getRate(spFrom.selectedItem.toString(), spTo.selectedItem.toString()).enqueue(object :
            Callback<JsonObject>{
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                var data = response.body()?.entrySet()?.find { it.key == spTo.selectedItem.toString() }
                val rate : Int = data?.value.toString().toInt()
                val conversion = etValueFrom.text.toString().toInt() * rate

                tvResultado.setText(conversion.toString())
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                println("Não foi possível realizar conversão")
            }

        })

    }
    fun getExchengers(){
        val retrofitClient = UtilsNetwork.getRetrofitInstance("https://cdn.jsdelivr.net/")
        val endpoint=retrofitClient.create(endpoint::class.java)

        endpoint.getexchange().enqueue(/* callback = */ object : Callback<JsonObject>{
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
               var data = mutableListOf<String>()

                response.body()?.keySet()?.iterator()?.forEach {
                    data.add(it)
                }

                val posBRL = data.indexOf("brl")
                val posUSD = data.indexOf("usd")

                val adapter = ArrayAdapter(baseContext, android.R.layout.simple_spinner_dropdown_item, data)
                spFrom.adapter=adapter
                spTo.adapter=adapter

                spFrom.setSelection(posBRL)
                spTo.setSelection(posUSD)
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
               println("Não foi possível realizar conversão")
            }

        })

    }
}