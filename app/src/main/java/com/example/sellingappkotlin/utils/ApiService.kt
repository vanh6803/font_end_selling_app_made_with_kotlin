package com.example.sellingappkotlin.utils

import com.example.sellingappkotlin.models.ApiResponseManufacturer
import com.example.sellingappkotlin.models.ApiResponseProduct
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {
    // todo: get manufacturer
    @GET("manufacturer")
    fun getListManufacturers(): Call<ApiResponseManufacturer>

    // todo: get product
    @GET("product")
    fun getListProduct(): Call<ApiResponseProduct>

    companion object {
        var baseUrl = "${Config.URL}/api/"
        fun create(): ApiService {
            val retrofit =
                Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(baseUrl)
                    .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}