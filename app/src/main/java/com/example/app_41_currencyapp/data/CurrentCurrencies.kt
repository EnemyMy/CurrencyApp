package com.example.app_41_currencyapp.data

data class CurrentCurrencies(
    var timestamp: Long,
    var base: String,
    var rates: Map<String, Double>
)