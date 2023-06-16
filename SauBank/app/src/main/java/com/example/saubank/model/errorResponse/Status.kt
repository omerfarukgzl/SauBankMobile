package com.example.saugetir.data.remote.model.errorResponse

data class Status (
    val success : Boolean? = null,
    val errorDescription : String? = null,
    val errorCode : String? = null
){
}