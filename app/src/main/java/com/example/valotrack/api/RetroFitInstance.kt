package com.example.valotrack.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    // PASTE YOUR API KEY HERE
    // Reads the key securely from your build configuration
    private const val API_KEY = com.example.valotrack.BuildConfig.API_KEY

    private const val BASE_URL = "https://api.henrikdev.xyz/"

    // This is the client that will intercept requests to add the key
    private val client = OkHttpClient.Builder().addInterceptor { chain ->
        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", API_KEY)
            .build()
        chain.proceed(newRequest)
    }.build()

    val api: ValorantApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client) // Set the custom client here
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ValorantApiService::class.java)
    }
}