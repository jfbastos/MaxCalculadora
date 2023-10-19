package com.zamfir.maxcalculadora.application

import android.app.Application
import com.zamfir.maxcalculadora.di.dataBaseModule
import com.zamfir.maxcalculadora.di.dispatcherModule
import com.zamfir.maxcalculadora.di.repositoryModule
import com.zamfir.maxcalculadora.di.useCaseModule
import com.zamfir.maxcalculadora.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MaxCalculadoraApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)
            modules(
                listOf(
                    dispatcherModule, dataBaseModule, repositoryModule, useCaseModule, viewModelModule
                )
            )
        }
    }
}
