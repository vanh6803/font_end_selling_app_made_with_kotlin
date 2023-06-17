package com.example.sellingappkotlin.utils

import com.example.sellingappkotlin.models.ApiResponseManufacturer
import com.example.sellingappkotlin.models.ApiResponseProduct
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {
    //todo: get manufacturer
    @get:GET("manufacturer")
    val listManufacturers: Call<ApiResponseManufacturer?>?

    //todo: get product
    @get:GET("product")
    val listProduct: Call<ApiResponseProduct?>?

    companion object {
        val gson = GsonBuilder().setDateFormat("dd-MM-yyyy").create()
        val apiService = Retrofit.Builder()
            .baseUrl("http://" + Config.LOCALHOST + ":" + Config.PORT + "/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService::class.java)
    }
}