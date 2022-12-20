package com.ads.app.exchangeconvert.util

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UtilsNetwork {
    companion object{
        fun getRetrofitInstance(path:String): Retrofit{
            return Retrofit.Builder()
                .baseUrl(path)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}