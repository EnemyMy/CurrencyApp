package com.example.app_41_currencyapp.data.remote

import com.example.app_41_currencyapp.data.CurrentCurrencies
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenExchangeApi {
    @GET("/api/latest.json")
    fun getRates(@Query("app_id") apiKey: String): Observable<CurrentCurrencies>

    @GET("/api/latest.json")
    fun getCurrency(@Query("app_id") apiKey: String, @Query("symbols") title: String): Observable<CurrentCurrencies>
}