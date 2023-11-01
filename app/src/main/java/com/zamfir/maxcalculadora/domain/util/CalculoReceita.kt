package com.zamfir.maxcalculadora.domain.util

object CalculoReceita {

    fun calculaDescontoInss(salario: Double) : Double{
        var desconto = 0.0

        if(salario > 1320.00) {
            desconto += (1320.0 * 0.075)
        }

        if(salario > 2571.29) {
            desconto += (2571.29 - 1320.0) * 0.09
        }else{
            desconto += (salario - 1320.0) * 0.09
            return desconto
        }

        if(salario > 3856.94) {
            desconto += (3856.94 - 2571.29) * 0.12
        }else{
            desconto += (salario - 2571.29) * 0.12
            return desconto
        }

        if(salario > 7507.94){
            desconto += (7507.94 -3856.94) * 0.14
        }else{
            desconto += (salario - 3856.94) * 0.14
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