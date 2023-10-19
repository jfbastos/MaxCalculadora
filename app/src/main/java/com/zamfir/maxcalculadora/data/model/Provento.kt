package com.zamfir.maxcalculadora.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Provento(
    val descricao : String,
    val valor : Double
){
    @PrimaryKey(autoGenerate = true) var id : Int = 0
}