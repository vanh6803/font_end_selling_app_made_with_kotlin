package com.example.sellingappkotlin.models

class Product(
    val _id: String,
    val name: String,
    val price: Int,
    val description: String,
    val detail: Detail,
    val color: MutableList<ColorProduct>,
    val quantity: Int,
    val status: String,
    val manufacturer: Manufacturer,
    val image: MutableList<String>,
)