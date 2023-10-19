package com.zamfir.maxcalculadora.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Historico(
    val idHistorico : Int,
    val descricao : String,
    val valor : String
){
    @PrimaryKey(autoGenerate = true) var id : Int = 0
}