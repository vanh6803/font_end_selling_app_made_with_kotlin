package com.example.sellingappkotlin.utils

import com.example.sellingappkotlin.models.Account
import com.example.sellingappkotlin.models.CheckEmail
import com.example.sellingappkotlin.models.User
import com.example.sellingappkotlin.models.responseApi.ApiResponseAccount
import com.example.sellingappkotlin.models.responseApi.ApiResponseLogin
import com.example.sellingappkotlin.models.responseApi.ApiResponseUser
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServiceUser {

    @POST("login")
    fun login(@Body account: Account): Call<ApiResponseLogin>

    @POST("register")
    fun register(@Body account: Account): Call<Account>

    @GET("account/check-email")
    fun checkEmail(@Query("email") email: String): Call<CheckEmail>

    @GET("logout")
    fun logout(@Header("Authorization") authToken: String ): Call<Void>

    @GET("profile")
    fun getProfile(@Header("Authorization") authToken: String): Call<ApiResponseUser>

    @PUT("account/update-profile/{id}")
    fun updateProfile(@Path("id") id:String  ,@Header("Authorization") authToken: String, @Body user:User): Call<Void>


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