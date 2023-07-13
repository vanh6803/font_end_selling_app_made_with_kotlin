package com.example.sellingappkotlin.utils

import com.example.sellingappkotlin.models.responseApi.ApiResponseManufacturer
import com.example.sellingappkotlin.models.responseApi.ApiResponseProduct
import com.example.sellingappkotlin.models.responseApi.ApiResponseProductDetail
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServiceSellingApp {
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
        private val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build()
        }

        val apiServiceSellingApp: ApiServiceSellingApp by lazy {
            retrofit.create(ApiServiceSellingApp::class.java)
        }

        }
    }