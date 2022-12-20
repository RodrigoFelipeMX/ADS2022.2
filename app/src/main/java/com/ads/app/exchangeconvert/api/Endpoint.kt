package com.ads.app.exchangeconvert.api

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface endpoint{
    @GET("/gh/fawazahmed0/currency-api@1/latest/currencies.json")
    fun getexchange(): Call<JsonObject>
    @GET("/gh/fawazahmed0/currency-api@1/latest/currencies/{from}/{to}.json")
    fun getRate(@Path(value = "from", encoded = true) from:String,@Path(value = "to", encoded = true) to :String): Call<JsonObject>


}