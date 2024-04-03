package com.lynn.quoteapp.data.source.network.services

import com.lynn.quoteapp.BuildConfig
import com.lynn.quoteapp.data.source.network.model.QuoteResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

interface QuoteApiServices {
    @GET("quotes")
    suspend fun getRandomQuotes(): List<QuoteResponse>

    companion object {
        @JvmStatic
        operator fun invoke(): QuoteApiServices {
            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
            return retrofit.create(QuoteApiServices::class.java)
        }
    }
}