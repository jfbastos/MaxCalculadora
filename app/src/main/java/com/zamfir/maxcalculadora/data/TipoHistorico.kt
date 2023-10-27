package com.zamfir.maxcalculadora.data

enum class TipoHistorico(val id : Int, val descricao : String) {
    TRIMESTRE (1, "TRIMESTRAL"),
    FERIAS (2, "FERIAS"),
    ANUAL (3, "ANUAL");

    companion object{
        fun getDescricao(id : Int){
            when(id){
                1 -> TRIMESTRE
                2 -> FERIAS
                3 -> ANUAL
            }
        }
    }
}