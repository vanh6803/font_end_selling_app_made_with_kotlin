package com.example.sellingappkotlin.models.responseApi

import com.example.sellingappkotlin.models.Product

class ApiResponseProduct(
    var data: MutableList<Product>,
    var message: String
)