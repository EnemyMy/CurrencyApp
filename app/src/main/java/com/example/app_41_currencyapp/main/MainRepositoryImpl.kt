package com.example.app_41_currencyapp.main

import com.example.app_41_currencyapp.data.ChosenCurrency
import com.example.app_41_currencyapp.data.CurrentCurrencies
import com.example.app_41_currencyapp.data.local.currencyvalues.CurrencyValuesDao
import com.example.app_41_currencyapp.data.remote.OpenExchangeApi
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(private val openExchangeApi: OpenExchangeApi, private val currencyValuesDao: CurrencyValuesDao): MainRepository {

    override fun getRates(apiKey: String): Observable<List<ChosenCurrency>> {
        return openExchangeApi.getRates(apiKey)
            .subscribeOn(Schedulers.io())
            .map { currencies ->
                convertToChosenCurrencies(currencies)
            }
            .zipWith(currencyValuesDao.getChosenCurrencies(), BiFunction { allCurrencies, savedCurrencies ->
                allCurrencies.filter { currency ->
                    savedCurrencies.any { savedCurrency -> savedCurrency.title == currency.title }
                }
            })
    }

    override fun deleteChosenCurrency(title: String): Completable {
        return currencyValuesDao.deleteChosenCurrency(ChosenCurrency(title, 0.0))
            .subscribeOn(Schedulers.io())
    }

    override fun saveChosenCurrency(title: String): Completable {
        return currencyValuesDao.insertChosenCurrency(ChosenCurrency(title, 0.0))
            .subscribeOn(Schedulers.io())
    }

    private fun convertToChosenCurrencies(currencies: CurrentCurrencies): List<ChosenCurrency> {
        return currencies.rates
            .map { currency ->
                ChosenCurrency(currency.key, currency.value)
            }
    }
}