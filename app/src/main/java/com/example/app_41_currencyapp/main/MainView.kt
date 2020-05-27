package com.example.app_41_currencyapp.main

import com.example.app_41_currencyapp.data.ChosenCurrency
import com.example.app_41_currencyapp.util.SnackbarEvent
import moxy.MvpView
import moxy.viewstate.strategy.alias.Skip

interface MainView: MvpView {
    fun showProgress()
    fun hideProgress()
    fun showList()
    fun hideList()
    fun submitNewList(list: List<ChosenCurrency>)
    fun showChooseChartsAnim()
    fun hideChooseChartsAnim()
    fun showNetworkErrorAnim()
    fun hideNetworkErrorAnim()
    @Skip
    fun showSnackbar(event: SnackbarEvent)
}