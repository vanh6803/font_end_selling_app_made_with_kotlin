package com.example.sellingappkotlin.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface ApiBill {

    fun getBill()

    companion object {
        var baseUrl = "${Config.URL}/api/"
        private val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build()
        }
        val apiBill: ApiBill by lazy {
            retrofit.create(ApiBill::class.java)
        }
    }

}