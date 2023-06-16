package com.example.saugetir.data.remote.model

import com.example.saugetir.data.remote.model.response.BankInfoResponse
import retrofit2.Call

class BankRepository(private var bankApi: BankApi)  {

    fun requestBankInfo(): Call<BankInfoResponse> {
       return bankApi.getBankInfo()
    }
}

/*class MerchantRepository(private var merchantApi: MerchantApi) {

    fun requestInitToken(request: InitRequest): Call<InitResponse> {
       return merchantApi.requestInitToken(request)
    }
}*/