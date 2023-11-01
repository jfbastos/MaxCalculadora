package com.zamfir.maxcalculadora.domain.model

data class FeriasVO(
    val salario : Double,
    val terco : Double,
    val inss : Double,
    val irrf : Double = 0.0,
    var total : Double = 0.0,
    var pecuniario : Double = 0.0,
    var tercoPecuniario : Double = 0.0,
    var adiantDecimo : Double = 0.0
)