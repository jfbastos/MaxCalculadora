package com.zamfir.maxcalculadora.viewmodel.state

data class TrimestralState(
    val isLoading : Boolean = false,
    val result : Double? = null,
    val error : String = ""
)