package com.zamfir.maxcalculadora.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zamfir.maxcalculadora.data.model.Trimestre

@Dao
interface TrimestreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(trimestre: Trimestre)

    @Query("SELECT * FROM trimestre")
    fun getAll() : List<Trimestre>
}