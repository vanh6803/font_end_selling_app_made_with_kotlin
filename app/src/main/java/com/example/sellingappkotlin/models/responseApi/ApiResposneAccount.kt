package com.example.sellingappkotlin.models.responseApi

import com.example.sellingappkotlin.models.Account


 class ApiResponseAccount(
    var data: MutableList<Account>,
    var message: String
)