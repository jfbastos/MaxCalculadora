package com.zamfir.maxcalculadora.viewmodel.state

data class AnualState(
    val isLoading : Boolean = false,
    val result : Double? = null,
    val error : Exception? = null
)