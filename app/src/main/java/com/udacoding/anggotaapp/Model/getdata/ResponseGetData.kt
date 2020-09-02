package com.udacoding.anggotaapp.Model.getdata

import com.google.gson.annotations.SerializedName

data class ResponseGetData(

    @field:SerializedName("isSucces")
    val isSucces: Boolean,

    @field:SerializedName("data")
    val data: List<DataItem>?,

    @field:SerializedName("message")
    val message: String
)