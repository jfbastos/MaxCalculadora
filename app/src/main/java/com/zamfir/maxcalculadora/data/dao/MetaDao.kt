package com.zamfir.maxcalculadora.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zamfir.maxcalculadora.data.model.Meta

@Dao
interface MetaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(meta : Meta)

    @Query("SELECT * FROM meta")
    fun getAll() : List<Meta>
}