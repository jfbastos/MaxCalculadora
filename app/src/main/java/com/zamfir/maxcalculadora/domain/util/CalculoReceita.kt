package com.zamfir.maxcalculadora.domain.util

object CalculoReceita {

    private const val PRIMEIRA_FAIXA_SALARIO_INSS = 1320.00
    private const val SEGUNDA_FAIXA_SALARIO_INSS = 2571.29
    private const val TERCEIRA_FAIXA_SALARIO_INSS = 3856.94
    private const val QUARTA_FAIXA_SALARIO_INSS = 7507.94

    private const val DESCONTO_PRIMEIRA_FAIXA_INSS = 0.075
    private const val DESCONTO_SEGUNDA_FAIXA_INSS = 0.09
    private const val DESCONTO_TERCEIRA_FAIXA_INSS = 0.12
    private const val DESCONTO_QUARTA_FAIXA_INSS = 0.14

    fun calculaDescontoInss(salario: Double) : Double{
        var desconto = 0.0

        if(salario <= PRIMEIRA_FAIXA_SALARIO_INSS) return desconto

        if(salario > PRIMEIRA_FAIXA_SALARIO_INSS) {
            desconto += (PRIMEIRA_FAIXA_SALARIO_INSS * DESCONTO_PRIMEIRA_FAIXA_INSS)
        }

        if(salario > SEGUNDA_FAIXA_SALARIO_INSS) {
            desconto += (SEGUNDA_FAIXA_SALARIO_INSS - PRIMEIRA_FAIXA_SALARIO_INSS) * DESCONTO_SEGUNDA_FAIXA_INSS
        }else{
            desconto += (salario - PRIMEIRA_FAIXA_SALARIO_INSS) * DESCONTO_SEGUNDA_FAIXA_INSS
            return desconto
        }

        if(salario > TERCEIRA_FAIXA_SALARIO_INSS) {
            desconto += (TERCEIRA_FAIXA_SALARIO_INSS - SEGUNDA_FAIXA_SALARIO_INSS) * DESCONTO_TERCEIRA_FAIXA_INSS
        }else{
            desconto += (salario - SEGUNDA_FAIXA_SALARIO_INSS) * DESCONTO_TERCEIRA_FAIXA_INSS
            return desconto
        }

        if(salario > QUARTA_FAIXA_SALARIO_INSS){
            desconto += (QUARTA_FAIXA_SALARIO_INSS - TERCEIRA_FAIXA_SALARIO_INSS) * DESCONTO_QUARTA_FAIXA_INSS
        }else{
            desconto += (salario - TERCEIRA_FAIXA_SALARIO_INSS) * DESCONTO_QUARTA_FAIXA_INSS
            return desconto
        }

        return desconto
    }

    fun calculaDescontoIrrf(salario: Double) : Double{
        if(salario <= 2112) return 0.0
        if(salario <= 2826.65) return (salario * 0.075) - 158.40
        if(salario <= 3751.05) return (salario * 0.15) - 370.40
        if(salario <= 4664.68) return (salario * 0.225) - 651.73
        return (salario * 0.275) - 884.96
    }
}