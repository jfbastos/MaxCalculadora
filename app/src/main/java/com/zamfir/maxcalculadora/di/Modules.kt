package com.zamfir.maxcalculadora.di

import androidx.room.Room
import com.zamfir.maxcalculadora.data.AppDatabase
import com.zamfir.maxcalculadora.data.repository.TrimestreRepository
import com.zamfir.maxcalculadora.data.repository.UserRepository
import com.zamfir.maxcalculadora.domain.usecase.TrimestreUseCase
import com.zamfir.maxcalculadora.domain.usecase.UserUseCase
import com.zamfir.maxcalculadora.util.Constants
import com.zamfir.maxcalculadora.viewmodel.TrimestreViewModel
import com.zamfir.maxcalculadora.viewmodel.UserViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.dsl.module

val dispatcherModule = module {
    single(named(Constants.IO_DISPATCHER)){ Dispatchers.IO }
}

val repositoryModule = module {
    single { TrimestreRepository(androidApplication() ,get(), get(named(Constants.IO_DISPATCHER))) }
    single { UserRepository(androidApplication() ,get(named(Constants.IO_DISPATCHER))) }
}

val useCaseModule = module{
    single { TrimestreUseCase(get(), get(named(Constants.IO_DISPATCHER)))}
    single { UserUseCase(get())}
}

val viewModelModule = module{
    viewModel { TrimestreViewModel(get()) }
    viewModel { UserViewModel(get()) }
}

val dataBaseModule = module {
    single { buildDatabase() }
    single { get<AppDatabase>().feriasDao() }
    single { get<AppDatabase>().historicoDao() }
    single { get<AppDatabase>().metaDao() }
    single { get<AppDatabase>().trimestreDao() }
}

private fun Scope.buildDatabase() = Room.databaseBuilder(
    get(),
    AppDatabase::class.java,
    Constants.DATABASE_NAME
).fallbackToDestructiveMigration().build()