package com.zamfir.maxcalculadora.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zamfir.maxcalculadora.data.model.Desconto

@Dao
interface DescontoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(desconto: Desconto)

    @Query("SELECT * FROM desconto")
    fun getAll() : List<Desconto>

}