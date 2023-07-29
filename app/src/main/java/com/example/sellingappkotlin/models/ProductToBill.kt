package com.example.sellingappkotlin.models

class ProductToBill(
    var _id: String,
    var uid: String,
    var name: String,
    var price: Int,
    var color: String,
    var quantity: Int,
    var status: String,
    var manufacturer: String,
    var image: String,
) {
    override fun toString(): String {
        return "$_id- $uid - $name - $price- $color - $status -  $manufacturer - $quantity -$image"
    }
}