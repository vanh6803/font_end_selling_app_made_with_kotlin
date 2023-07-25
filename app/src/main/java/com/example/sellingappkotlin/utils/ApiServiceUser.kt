package com.example.sellingappkotlin.utils

import com.example.sellingappkotlin.models.Account
import com.example.sellingappkotlin.models.CheckEmail
import com.example.sellingappkotlin.models.User
import com.example.sellingappkotlin.models.responseApi.ApiResponseAccount
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiServiceUser {

    @POST("login")
    fun login(@Body account: Account): Call<Account>

    @POST("register")
    fun register(@Body account: Account): Call<Account>

    @GET("account/check-email")
    fun checkEmail(@Query("email") email: String): Call<CheckEmail>


    companion object{
        var baseUrl = "${Config.URL}/api/"
        private val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build()
        }
        val apiServiceUser: ApiServiceUser by lazy {
            retrofit.create(ApiServiceUser::class.java)
        }
    }
}