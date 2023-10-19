package com.zamfir.maxcalculadora.di

import androidx.room.Room
import com.zamfir.maxcalculadora.data.AppDatabase
import com.zamfir.maxcalculadora.data.repository.TrimestreRepository
import com.zamfir.maxcalculadora.domain.usecase.TrimestreUseCase
import com.zamfir.maxcalculadora.util.Constants
import com.zamfir.maxcalculadora.viewmodel.TrimestreViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.dsl.module

val dispatcherModule = module {
    single(named(Constants.IO_DISPATCHER)){ Dispatchers.IO }
}

val repositoryModule = module {
    single { TrimestreRepository(get(), get(named(Constants.IO_DISPATCHER))) }
}

val useCaseModule = module{
    single { TrimestreUseCase(get())}
}

val viewModelModule = module{
    viewModel { TrimestreViewModel(get()) }
}

val dataBaseModule = module {
    single { buildDatabase() }
    single { get<AppDatabase>().descontoDao() }
    single { get<AppDatabase>().feriasDao() }
    single { get<AppDatabase>().historicoDao() }
    single { get<AppDatabase>().metaDao() }
    single { get<AppDatabase>().proventoDao() }
    single { get<AppDatabase>().salarioDao() }
    single { get<AppDatabase>().trimestreDao() }
}

private fun Scope.buildDatabase() = Room.databaseBuilder(
    get(),
    AppDatabase::class.java,
    Constants.DATABASE_NAME
).fallbackToDestructiveMigration().build()