package com.zamfir.maxcalculadora.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zamfir.maxcalculadora.data.model.Provento

@Dao
interface ProventoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(provento: Provento)

    @Query("SELECT * FROM provento")
    fun getAll() : List<Provento>
}