package com.example.app_41_currencyapp.application

import android.app.Application
import com.example.app_41_currencyapp.data.local.CurrenciesDatabase
import com.example.app_41_currencyapp.data.local.currencyvalues.CurrencyValuesDao
import com.example.app_41_currencyapp.data.remote.OpenExchangeApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
abstract class AppModule {
    companion object {
        @Provides
        @Singleton
        fun provideOpenExchangeApi(): OpenExchangeApi {
            val interceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            }
            val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://openexchangerates.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
            return retrofit.create(OpenExchangeApi::class.java)
        }

        @Provides
        @Singleton
        fun provideCurrencyValuesDao(application: Application): CurrencyValuesDao {
            val database = CurrenciesDatabase.getCurrenciesDatabase(application.applicationContext)
            return database.currencyValuesDao()
        }
    }
}