package com.zamfir.maxcalculadora.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Meta(
    val mesAdmissao : String,
    val metaAcancada : Double,
    val isCalculoParcial : Boolean
){
    @PrimaryKey(autoGenerate = true) var id : Int = 0
}