package com.yusuforhan.budgetmanager.android.Service

import com.yusuforhan.budgetmanager.android.Model.CryptoModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Query

class CurrencyService {
    //https://raw.githubusercontent.com/atilsamancioglu/IA32-CryptoComposeData/main/cryptolist.json
    private val BASE_URL = "https://raw.githubusercontent.com/"
    private val call = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build().create(CurrencyApi::class.java)
    suspend fun getData() : Response<List<CryptoModel>>{
        return call.getData()
    }
    suspend fun getQueryData(query: String) : Call<List<CryptoModel>>{
        return call.query(query)
    }

}