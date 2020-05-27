package com.example.app_41_currencyapp.main

import android.annotation.SuppressLint
import android.util.Log
import com.example.app_41_currencyapp.data.ChosenCurrency
import com.example.app_41_currencyapp.util.SnackbarEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import moxy.MvpPresenter
import java.net.UnknownHostException
import javax.inject.Inject

class MainPresenter @Inject constructor(private val repository: MainRepository): MvpPresenter<MainView>() {

    @SuppressLint("CheckResult")
    fun onQueryCurrency(apiKey: String, oldList: List<ChosenCurrency>) {
        viewState.showProgress()
        viewState.hideChooseChartsAnim()
        viewState.hideNetworkErrorAnim()
        repository.getRates(apiKey)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({currencies ->
                viewState.submitNewList(currencies)
                if (currencies.isEmpty())
                    viewState.showChooseChartsAnim()
                else
                    viewState.showList()
                viewState.hideProgress()
            }, {error ->
                viewState.hideProgress()
                viewState.submitNewList(oldList)
                when(error) {
                    is UnknownHostException -> {
                        viewState.hideList()
                        viewState.showNetworkErrorAnim()
                    }
                    else -> {
                        viewState.hideList()
                        viewState.showChooseChartsAnim()
                        viewState.showSnackbar(SnackbarEvent("Error while loading currencies", actionText = "Try again", action = {
                            onQueryCurrency(apiKey, oldList)
                        }))
                    }
                }
                Log.e("Error:", "onQueryCurrency: $error")
            })
    }

    @SuppressLint("CheckResult")
    fun onDeleteChosenCurrency(position: Int, oldList: List<ChosenCurrency>) {
        viewState.showProgress()
        repository.deleteChosenCurrency(oldList[position].title)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewState.submitNewList(oldList.minus(oldList[position]))
                viewState.hideProgress()
                if (oldList.size == 1) {
                    viewState.hideList()
                    viewState.showChooseChartsAnim()
                }
                viewState.showSnackbar(SnackbarEvent("Currency successfully deleted", actionText = "Undo", action = {
                    onSaveChosenCurrency(oldList[position].title)
                    viewState.submitNewList(oldList)
                    viewState.showList()
                    viewState.hideChooseChartsAnim()
                }))
            }, {error ->
                Log.e("Error:", "$error")
                viewState.submitNewList(oldList)
                viewState.hideProgress()
                viewState.showSnackbar(SnackbarEvent("Error while trying to delete currency"))
            })
    }

    @SuppressLint("CheckResult")
    fun onSaveChosenCurrency(title: String) {
        viewState.showProgress()
        repository.saveChosenCurrency(title)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewState.hideProgress()
            }, {error ->
                Log.e("Error:", "$error")
                viewState.hideProgress()
                viewState.showSnackbar(SnackbarEvent("Error while adding currency", actionText = "Try again", action = {
                    onSaveChosenCurrency(title)
                }))
            })
    }
}