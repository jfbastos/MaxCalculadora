package com.zamfir.maxcalculadora.data.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class Salario(
    val bonusTempoServico : Double,
    val planoDeSaude : Double,
    val porcentagemDescontoInss : Double,
    val porcentagemDescontoIrrf : Double,
    val porcentagemDescontoAlimentacao : Double
){
    @PrimaryKey(autoGenerate = true) var id : Int = 0
    @Ignore val listaDeProventos : MutableList<Provento> = mutableListOf()
    @Ignore val listaDeDescontos : MutableList<Desconto> = mutableListOf()
}