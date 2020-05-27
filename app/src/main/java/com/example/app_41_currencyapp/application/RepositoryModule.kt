package com.example.app_41_currencyapp.application

import com.example.app_41_currencyapp.addcurrency.AddCurrencyRepository
import com.example.app_41_currencyapp.addcurrency.AddCurrencyRepositoryImpl
import com.example.app_41_currencyapp.main.MainRepository
import com.example.app_41_currencyapp.main.MainRepositoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindAddCurrencyRepository(repository: AddCurrencyRepositoryImpl): AddCurrencyRepository

    @Binds
    @Singleton
    abstract fun bindMainRepository(repository: MainRepositoryImpl): MainRepository
}