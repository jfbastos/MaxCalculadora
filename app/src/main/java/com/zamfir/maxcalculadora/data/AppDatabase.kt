package com.zamfir.maxcalculadora.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zamfir.maxcalculadora.data.dao.*
import com.zamfir.maxcalculadora.data.model.*

@Database(
    entities = [
        Trimestre::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase(){
    abstract fun trimestreDao()  : TrimestreDao
}