package com.example.app_41_currencyapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ChosenCurrency(
    @PrimaryKey var title: String,
    var value: Double
)