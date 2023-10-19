package com.zamfir.maxcalculadora.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Ferias(
    val diasFerias : Int,
    val isAdiantamento : Int,
    val porcetagemDescontoInss : Double
){
    companion object{
        const val porcetagemFerias = 0.3
    }

    @PrimaryKey(autoGenerate = true) var id : Int = 0
}
