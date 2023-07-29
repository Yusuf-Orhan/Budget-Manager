package com.yusuforhan.budgetmanager.android.Service

import com.yusuforhan.budgetmanager.android.Model.CryptoModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {
    @GET("atilsamancioglu/IA32-CryptoComposeData/main/cryptolist.json")
    suspend fun getData() : Response<List<CryptoModel>>
    @GET("atilsamancioglu/IA32-CryptoComposeData/main/cryptolist.json")
    fun query(@Query("q") query: String) : Call<List<CryptoModel>>
}