package com.example.app_41_currencyapp.addcurrency

import com.example.app_41_currencyapp.data.ChosenCurrency
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface AddCurrencyRepository {
    fun getCurrency(apiKey: String, query: String): Observable<ChosenCurrency>
    fun saveChosenCurrency(title: String): Completable
}