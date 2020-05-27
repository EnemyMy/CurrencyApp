package com.example.app_41_currencyapp.addcurrency

import android.annotation.SuppressLint
import android.content.Context
import com.example.app_41_currencyapp.data.ChosenCurrency
import com.example.app_41_currencyapp.data.CurrentCurrencies
import com.example.app_41_currencyapp.data.InvalidQueryException
import com.example.app_41_currencyapp.data.local.CurrenciesDatabase
import com.example.app_41_currencyapp.data.local.currencyvalues.CurrencyValuesDao
import com.example.app_41_currencyapp.data.remote.OpenExchangeApi
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AddCurrencyRepositoryImpl @Inject constructor(private val openExchangeApi: OpenExchangeApi, private val currencyValuesDao: CurrencyValuesDao): AddCurrencyRepository {

    @SuppressLint("CheckResult")
    override fun getCurrency(apiKey: String, query: String): Observable<ChosenCurrency> {
        return openExchangeApi.getCurrency(apiKey, query)
            .subscribeOn(Schedulers.io())
            .doOnNext { currencies ->
                checkIfQueryValid(currencies)
            }
            .map {currencies ->
                convertToChosenCurrency(currencies, query)
            }
    }

    override fun saveChosenCurrency(title: String): Completable {
        return currencyValuesDao.insertChosenCurrency(ChosenCurrency(title, 0.0))
            .subscribeOn(Schedulers.io())
    }

    private fun convertToChosenCurrency(currencies: CurrentCurrencies, title: String): ChosenCurrency {
        return ChosenCurrency(title, currencies.rates[title] ?: 0.0)
    }

    private fun checkIfQueryValid(currencies: CurrentCurrencies) {
        if (currencies.rates.isEmpty()) throw InvalidQueryException()
    }


}