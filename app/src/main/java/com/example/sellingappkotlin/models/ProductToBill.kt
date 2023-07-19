package com.example.sellingappkotlin.models

class ProductToBill(
    val _id: String,
    val name: String,
    val price: Int,
    val color: String,
    val quantity: Int,
    val status: String,
    val manufacturer: String,
    val image: String,
) {
    override fun toString(): String {
        return "$_id - $name - $price- $color - $status -  $manufacturer - $quantity -$image"
    }
}