package com.example.app_41_currencyapp.application

import android.app.Application
import com.example.app_41_currencyapp.addcurrency.AddCurrencyFragment
import com.example.app_41_currencyapp.main.MainFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, RepositoryModule::class, PresenterModule::class])
interface AppComponent {

    fun inject(fragment: MainFragment)
    fun inject(fragment: AddCurrencyFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }

}