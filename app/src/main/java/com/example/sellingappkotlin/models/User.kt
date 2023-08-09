package com.example.sellingappkotlin.models

import java.io.Serializable

class User(
    var _id: String,
    var fullName: String,
    var username: String,
    var email: String,
    var password: String,
    var address: String,
    var gender: Int,
    var role: Int,
    var phone: String,
    var avatar: String,
    var birthday: String,
    var token: String
): Serializable{
    override fun toString(): String {
        return "User(_id='$_id', fullName='$fullName', username='$username', email='$email', password='$password', address='$address', gender='$gender', role=$role, phone='$phone', avatar='$avatar', birthday='$birthday', token='$token')"
    }
}