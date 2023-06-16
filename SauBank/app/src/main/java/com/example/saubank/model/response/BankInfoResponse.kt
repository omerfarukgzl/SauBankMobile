package com.example.saugetir.data.remote.model.response

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class BankInfoResponse(
    @SerializedName("amount")
    val amount: BigDecimal? = null
) {
}