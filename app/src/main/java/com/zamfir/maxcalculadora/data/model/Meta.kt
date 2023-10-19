package com.zamfir.maxcalculadora.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Meta(
    val meta : Int,
    val valorPonto : Double,
    val realizado : Double
){
    @PrimaryKey(autoGenerate = true) var id : Int = 0
}