package com.example.sellingappkotlin.models

data class Bill (
    val _id: String,
    val user: User,
    var products: List<BillProduct>,
    val totalAmount: Double
)