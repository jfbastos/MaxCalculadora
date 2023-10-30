package com.zamfir.maxcalculadora.domain.model

data class TrimestreVO(
    val valorPrimeiroTrimestre : String,
    val valorSegundoTrimestre : String,
    val valorTerceiroTrimestre : String,
    val valorQuartoTrimestre : String,
    val metaAtingida : String,
    val isCalculoParcial : Boolean,
    val dataAdmissao : String,
)
