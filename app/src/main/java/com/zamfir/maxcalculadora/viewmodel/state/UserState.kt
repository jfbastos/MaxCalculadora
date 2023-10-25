package com.zamfir.maxcalculadora.viewmodel.state

data class UserState(
    val isLoading : Boolean = false,
    val isSuccess : Boolean = false,
    val error : Exception? = null
)