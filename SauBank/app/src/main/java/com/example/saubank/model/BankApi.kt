package com.example.saugetir.data.remote.model

import com.example.saugetir.data.remote.model.response.BankInfoResponse
import retrofit2.http.GET

interface BankApi {

    @GET("getBank")
    fun getBankInfo (): retrofit2.Call<BankInfoResponse>

}