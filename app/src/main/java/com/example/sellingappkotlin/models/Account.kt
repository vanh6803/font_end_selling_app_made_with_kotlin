package com.example.sellingappkotlin.models

class Account(
    var email: String,
    var password: String,
    var role: Int
){
    override fun toString(): String {
        return "Account(email='$email', password='$password', role=$role)"
    }
}