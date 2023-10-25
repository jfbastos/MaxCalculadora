package com.zamfir.maxcalculadora.util

object UtilData {

    fun getMesAdmissao(mesAdmissao: String) : Int{
        return when(mesAdmissao){
            "Janeiro" -> 1
            "Fevereiro" -> 2
            "Março" -> 3
            "Abril" -> 4
            "Maio" -> 5
            "Junho" -> 6
            "Julho" -> 7
            "Agosto" -> 8
            "Setembro" -> 9
            "Outubro" -> 10
            "Novembro" -> 11
            "Dezembro" -> 12
            else -> 0
        }
    }

}