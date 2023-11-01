package com.zamfir.maxcalculadora.domain.usecase

import com.zamfir.maxcalculadora.data.repository.UserRepository
import com.zamfir.maxcalculadora.domain.model.FeriasVO
import com.zamfir.maxcalculadora.domain.util.CalculoReceita
import com.zamfir.maxcalculadora.util.roundUp

class FeriasUseCase (userRepository: UserRepository){

    val salario = userRepository.getUsuario().salario

    operator fun invoke(diasFerias : Int, isAbono : Boolean, isAdiantamento : Boolean) : FeriasVO{
        val result = if(diasFerias != 30) calculaFeriasFracionada(diasFerias) else calculaFerias()

        if(isAbono){
            result.apply {
                pecuniario = calculaUmTerco(salario).roundUp()
                tercoPecuniario = calculaUmTerco(pecuniario).roundUp()
                total += pecuniario + tercoPecuniario
            }
        }

        if(isAdiantamento) {
            result.apply {
                adiantDecimo = (salario / 2).roundUp()
                total += adiantDecimo
            }
        }

        return result
    }

    private fun calculaFerias() : FeriasVO{
        val salarioBase = calculaSalarioBase()
        val descontoInss = CalculoReceita.calculaDescontoInss(salarioBase)
        val descontoIrrf = CalculoReceita.calculaDescontoIrrf(salarioBase - descontoInss)
        return FeriasVO(salario = salario.roundUp(), terco = calculaUmTerco(salario).roundUp(), inss = descontoInss.roundUp(), irrf = descontoIrrf.roundUp()).apply {
            this.total = ((salario + terco) - (inss + irrf)).roundUp()
        }
    }

    private fun calculaFeriasFracionada(dias : Int) : FeriasVO{
        val salarioParcial = (salario / 30) * dias
        val salarioBase = calculaSalarioBase(salarioParcial)
        val descontoInss = CalculoReceita.calculaDescontoInss(salarioBase)
        val descontoIrrf = CalculoReceita.calculaDescontoIrrf(salarioBase - descontoInss)
        return FeriasVO(salario = salarioParcial.roundUp(), terco = calculaUmTerco(salarioParcial).roundUp(), inss = descontoInss.roundUp(), irrf = descontoIrrf.roundUp()).apply {
            this.total = ((salario + terco) - (inss + irrf)).roundUp()
        }
    }

    private fun calculaSalarioBase(salarioParcial : Double = salario) : Double{
        val umTerco = calculaUmTerco(salarioParcial)
        return salarioParcial.plus(umTerco)
    }

    private fun calculaUmTerco(salario : Double) = salario / 3

}