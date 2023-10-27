package com.zamfir.maxcalculadora.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Historico(
    val idTipoHistorico : Int,
    val valor : String
){
    @PrimaryKey(autoGenerate = true) var id : Int = 0
}