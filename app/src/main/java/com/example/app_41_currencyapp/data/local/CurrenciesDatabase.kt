package com.example.app_41_currencyapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.app_41_currencyapp.data.ChosenCurrency
import com.example.app_41_currencyapp.data.local.currencyvalues.CurrencyValuesDao

@Database(entities = [ChosenCurrency::class], version = 1)
abstract class CurrenciesDatabase : RoomDatabase() {

    abstract fun currencyValuesDao(): CurrencyValuesDao

    companion object {
        private var INSTANCE: CurrenciesDatabase? = null

        fun getCurrenciesDatabase(context: Context): CurrenciesDatabase {
            if (INSTANCE == null) {
                synchronized(CurrenciesDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, CurrenciesDatabase::class.java, "myDB").build()
                }
            }
            return INSTANCE!!
        }
    }
}