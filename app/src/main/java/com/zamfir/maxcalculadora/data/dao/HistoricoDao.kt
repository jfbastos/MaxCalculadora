package com.zamfir.maxcalculadora.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zamfir.maxcalculadora.data.model.Historico

@Dao
interface HistoricoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(historico: Historico)

    @Query("SELECT * FROM historico")
    fun getAll() : List<Historico>

}