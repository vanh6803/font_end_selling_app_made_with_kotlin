package com.example.sellingappkotlin.models.responseApi

import com.example.sellingappkotlin.models.User
import com.google.gson.annotations.SerializedName

class ApiResponseLogin(
    var status: Int,
    var message: String,
    var data: User
){
    override fun toString(): String {
        return "ApiResponseLogin(status=$status, message='$message', data=$data)"
    }
}