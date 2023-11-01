package com.zamfir.maxcalculadora.viewmodel.state

import com.zamfir.maxcalculadora.domain.model.FeriasVO

data class FeriasState(
    val isLoading : Boolean = false,
    val result : FeriasVO? = null,
    val error : Exception? = null
)