package com.example.app_41_currencyapp.addcurrency

import com.example.app_41_currencyapp.util.SnackbarEvent
import moxy.MvpView
import moxy.viewstate.strategy.alias.Skip

interface AddCurrencyView: MvpView {

    fun showProgress()
    fun hideProgress()
    fun showResult(title: String, value: String)
    fun hideResult()
    fun hideAddCurrencyAnim()
    fun showNoResult()
    fun hideNoResult()
    fun showNetworkErrorAnim()
    fun hideNetworkErrorAnim()
    @Skip
    fun showSnackbar(event: SnackbarEvent)

}