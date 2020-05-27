package com.example.app_41_currencyapp.addcurrency

import android.annotation.SuppressLint
import android.util.Log
import com.example.app_41_currencyapp.util.SnackbarEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import moxy.MvpPresenter
import java.net.UnknownHostException
import java.util.*
import javax.inject.Inject

class AddCurrencyPresenter @Inject constructor(private val repository: AddCurrencyRepository): MvpPresenter<AddCurrencyView>() {

    @SuppressLint("CheckResult")
    fun onQueryCurrency(apiKey: String, query: String?) {
        viewState.hideAddCurrencyAnim()
        viewState.hideNoResult()
        viewState.hideNetworkErrorAnim()
        viewState.hideResult()
        viewState.showProgress()
        if (query == null || query == "\"\"") {
            viewState.hideProgress()
            viewState.showNoResult()
            return
        }
        repository.getCurrency(apiKey, query.toUpperCase())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ currency ->
                viewState.showResult(currency.title, String.format(Locale.US, "%.2f", currency.value))
                viewState.hideProgress()
            }, {error ->
                Log.e("Error:", "$error")
                when(error) {
                    is UnknownHostException -> viewState.showNetworkErrorAnim()
                    else -> viewState.showNoResult()
                }
                viewState.hideProgress()
            })
    }

    @SuppressLint("CheckResult")
    fun onSaveChosenCurrency(title: String) {
        viewState.showProgress()
        repository.saveChosenCurrency(title)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewState.hideProgress()
                viewState.showSnackbar(SnackbarEvent("Currency successfully added"))
            }, {error ->
                Log.e("Error:", "$error")
                viewState.hideProgress()
                viewState.showSnackbar(SnackbarEvent("Error while adding currency", actionText = "Try again", action = {
                    onSaveChosenCurrency(title)
                }))
            })
    }

}