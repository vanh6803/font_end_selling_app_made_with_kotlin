package com.example.sellingappkotlin.utils

import com.example.sellingappkotlin.models.ApiResponseManufacturer
import com.example.sellingappkotlin.models.ApiResponseProduct
import com.example.sellingappkotlin.models.ApiResponseProductDetail
import com.example.sellingappkotlin.models.Product
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryName

interface ApiService {
    // todo: get manufacturer
    @GET("manufacturer")
    fun getListManufacturers(): Call<ApiResponseManufacturer>

    // todo: get product
    @GET("product")
    fun getListProduct(): Call<ApiResponseProduct>

    // todo: get product
    @GET("product")
    fun getListProductFormManufacturers(@Query("manufacturer") manufacturer: String): Call<ApiResponseProduct>

    @GET("product/detail/{id}")
    fun getProductFromId(@Path("id") id: String): Call<ApiResponseProductDetail>

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