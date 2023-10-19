package com.zamfir.maxcalculadora.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zamfir.maxcalculadora.data.model.Ferias

@Dao
interface FeriasDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(ferias: Ferias)

    @Query("SELECT * FROM ferias")
    fun getAll() : List<Ferias>


}