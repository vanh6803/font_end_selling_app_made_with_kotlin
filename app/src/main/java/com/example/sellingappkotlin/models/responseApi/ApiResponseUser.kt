package com.example.sellingappkotlin.models.responseApi

import com.example.sellingappkotlin.models.User

data class ApiResponseUser(
    var status: Int,
    var data: User,
    var message: String
)
