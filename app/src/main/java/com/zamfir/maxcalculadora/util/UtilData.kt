package com.zamfir.maxcalculadora.util

import java.time.LocalDate

object UtilData {

    fun getMesAdmissao(mesAdmissao: String) : Int{
        return when(mesAdmissao){
            "Janeiro" -> 0
            "Fevereiro" -> 1
            "Março" -> 2
            "Abril" -> 3
            "Maio" -> 4
            "Junho" -> 5
            "Julho" -> 6
            "Agosto" -> 7
            "Setembro" -> 8
            "Outubro" -> 9
            "Novembro" -> 10
            "Dezembro" -> 11
            else -> 0
        }
    }

    fun getMesesTrabalhadosPorTrimestre() : Int {
        if(LocalDate.now().monthValue in 1..4) return 3
        if(LocalDate.now().monthValue in 5..7) return 6
        if(LocalDate.now().monthValue in 8..10) return 9
        return 12
    }

}