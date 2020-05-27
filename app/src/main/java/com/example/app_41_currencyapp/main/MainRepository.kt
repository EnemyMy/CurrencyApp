package com.example.app_41_currencyapp.main

import com.example.app_41_currencyapp.data.ChosenCurrency
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface MainRepository {
    fun getRates(apiKey: String): Observable<List<ChosenCurrency>>
    fun deleteChosenCurrency(title: String): Completable
    fun saveChosenCurrency(title: String): Completable
}