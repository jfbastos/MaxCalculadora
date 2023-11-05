package com.zamfir.maxcalculadora.viewmodel.state

import com.zamfir.maxcalculadora.data.model.Usuario

data class UserState(
    val isLoading : Boolean = false,
    val usuario : Usuario? = null,
    val error : Exception? = null
)