package com.example.sellingappkotlin.models

class Product(
    val _id: String,
    val name: String,
    val price: Int,
    val description: String,
    val detail: Detail,
    val color: String,
    val quantity: Int,
    val status: String,
    val manufacturer: Manufacturer,
    val image: String,
)