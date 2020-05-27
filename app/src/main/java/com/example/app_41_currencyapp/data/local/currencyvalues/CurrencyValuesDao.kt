package com.example.app_41_currencyapp.data.local.currencyvalues

import androidx.room.*
import com.example.app_41_currencyapp.data.ChosenCurrency
import com.example.app_41_currencyapp.data.CurrentCurrencies
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface CurrencyValuesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertChosenCurrency(currency: ChosenCurrency): Completable

    @Delete
    fun deleteChosenCurrency(currency: ChosenCurrency): Completable

    @Query("SELECT * FROM ChosenCurrency ORDER BY title")
    fun getChosenCurrencies(): Observable<List<ChosenCurrency>>
}