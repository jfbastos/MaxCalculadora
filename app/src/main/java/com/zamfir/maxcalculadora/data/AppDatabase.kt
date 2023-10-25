package com.zamfir.maxcalculadora.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zamfir.maxcalculadora.data.dao.*
import com.zamfir.maxcalculadora.data.model.*

@Database(
    entities = [
        Ferias::class,
        Historico::class,
        Meta::class,
        Trimestre::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase(){
    abstract fun feriasDao() : FeriasDao
    abstract fun historicoDao() : HistoricoDao
    abstract fun metaDao() : MetaDao
    abstract fun trimestreDao()  : TrimestreDao
}