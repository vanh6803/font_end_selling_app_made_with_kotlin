package com.example.sellingappkotlin.utils

import com.example.sellingappkotlin.models.responseApi.ApiResponseAccount
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.POST

interface ApiServiceUser {

    @POST("login")
    fun login(): Call<ApiResponseAccount>

    @POST("register")
    fun register(): Call<ApiResponseAccount>

    companion object{
        var baseUrl = "${Config.URL}/api"
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