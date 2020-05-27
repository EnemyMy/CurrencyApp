package com.example.app_41_currencyapp.application

import com.example.app_41_currencyapp.addcurrency.AddCurrencyPresenter
import com.example.app_41_currencyapp.addcurrency.AddCurrencyRepository
import com.example.app_41_currencyapp.main.MainPresenter
import com.example.app_41_currencyapp.main.MainRepository
import dagger.Module
import dagger.Provides

@Module
abstract class PresenterModule {
    companion object {
        @Provides
        fun provideMainPresenter(repository: MainRepository): MainPresenter {
            return MainPresenter(repository)
        }

        @Provides
        fun provideAddCurrencyPresenter(repository: AddCurrencyRepository): AddCurrencyPresenter {
            return AddCurrencyPresenter(repository)
        }
    }
}