
package com.example.idtex.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * The RetrofitInstance object provides the Retrofit setup
 * for network operations including base URL and logging support.
 */
object RetrofitInstance {
    /**
     * Base URL for the API.
     */
    const val BASE_URL = "https://hspndlurfbnyeuswdpkv.supabase.co/"

    /**
     * HTTP logging interceptor for logging network request and response data.
     */
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    /**
     * OkHttpClient with attached logging interceptor.
     */
    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    /**
     * Retrofit instance configured with base URL and Gson converter.
     */
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    /**
     * API interface for making network calls.
     */
    val api: ApiInterface = retrofit.create(ApiInterface::class.java)
}