package com.zamfir.maxcalculadora.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zamfir.maxcalculadora.data.dao.*
import com.zamfir.maxcalculadora.data.model.*

@Database(
    entities = [
        Desconto::class,
        Ferias::class,
        Historico::class,
        Meta::class,
        Provento::class,
        Salario::class,
        Trimestre::class
    ],
    version = 2
)
abstract class AppDatabase : RoomDatabase(){
    abstract fun descontoDao() : DescontoDao
    abstract fun feriasDao() : FeriasDao
    abstract fun historicoDao() : HistoricoDao
    abstract fun metaDao() : MetaDao
    abstract fun proventoDao() : ProventoDao
    abstract fun salarioDao() : SalarioDao
    abstract fun trimestreDao()  : TrimestreDao
}