package com.example.sellingappkotlin.models

import java.io.Serializable

class Product(
    val _id: String,
    val name: String,
    val price: Int,
    val description: String,
    val detail: Detail,
    val color: MutableList<String>,
    val quantity: Int,
    val status: String,
    val manufacturer: Manufacturer,
    val image: String,
): Serializable