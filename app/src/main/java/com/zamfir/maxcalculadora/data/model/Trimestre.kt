package com.zamfir.maxcalculadora.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Trimestre(
    val valorPrimeiroTrimestre : Double,
    val valorSegundoTrimestre : Double,
    val valorTerceiroTrimestre : Double,
    val valorQuartoTrimestre : Double,
    val metaAtingida : Double,
    val isCalculoParcial : Boolean,
    val dataAdmissao : String,
){
   @PrimaryKey(autoGenerate = true) var id : Int = 0
}