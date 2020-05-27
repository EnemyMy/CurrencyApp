package com.example.app_41_currencyapp.application

import android.app.Application

class CurrencyApplication: Application() {
    val appComponent by lazy { DaggerAppComponent.factory().create(this) }
}