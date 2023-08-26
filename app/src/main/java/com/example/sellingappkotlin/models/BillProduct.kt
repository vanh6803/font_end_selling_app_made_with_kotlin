package com.example.sellingappkotlin.models

data class BillProduct (
    val product: Product,
    val quantity: Int
){
    override fun toString(): String {
        return "BillProduct(product=$product, quantity=$quantity)"
    }
}