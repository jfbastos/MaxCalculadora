package com.zamfir.maxcalculadora.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zamfir.maxcalculadora.data.model.Salario

@Dao
interface SalarioDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(salario: Salario)

    @Query("SELECT * FROM salario")
    fun getAll() : List<Salario>
}