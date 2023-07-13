package com.example.sellingappkotlin.models.responseApi

import com.example.sellingappkotlin.models.Manufacturer

class ApiResponseManufacturer(
    var data: MutableList<Manufacturer>,
    var message: String
)