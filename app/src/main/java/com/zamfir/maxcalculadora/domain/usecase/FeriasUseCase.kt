package com.zamfir.maxcalculadora.domain.usecase

import com.zamfir.maxcalculadora.data.repository.UserRepository
import com.zamfir.maxcalculadora.domain.exception.AbonoPecuniarioException
import com.zamfir.maxcalculadora.domain.exception.QuantidadeDiasException
import com.zamfir.maxcalculadora.domain.model.FeriasVO
import com.zamfir.maxcalculadora.domain.util.CalculoReceita
import com.zamfir.maxcalculadora.util.roundUp

class FeriasUseCase (private val userRepository: UserRepository){

    @Throws
    operator fun invoke(diasFerias : Int, isAbono : Boolean, isAdiantamento : Boolean) : FeriasVO{

        isCalculoValido(diasFerias, isAbono)

        val salario = userRepository.getUsuario().salario
        val result : FeriasVO?

        if(isAbono){
            result = calculaFeriasPecuniario(salario).apply {
                pecuniario = calculaUmTerco(salario).roundUp()
                tercoPecuniario = calculaUmTerco(pecuniario).roundUp()
                total += pecuniario + tercoPecuniario
            }
        }else{
            result = if(diasFerias != 30) calculaFeriasFracionada(salario,diasFerias) else calculaFerias(salario)
        }

        if (isAdiantamento) {
            result.apply {
                adiantDecimo = (salario / 2).roundUp()
                total += adiantDecimo
            }
        }

        return result
    }

    private fun isCalculoValido(dias: Int, isAbono: Boolean) {
         when {
             dias < 5 -> throw QuantidadeDiasException("A quantidade de dias não pode ser menor do que 5.")
             dias > 30 -> throw QuantidadeDiasException("A quantidade de dias não pode ser maior do que 30.")
             dias < 30 && isAbono -> throw AbonoPecuniarioException("Para que o abono pecuniário possa ser solicitado, é necessário tirar 30 dias de férias.")
        }
    }

    private fun calculaFerias(salario: Double) : FeriasVO{
        val salarioBase = calculaSalarioBase(salario)
        val descontoInss = CalculoReceita.calculaDescontoInss(salarioBase)
        val descontoIrrf = CalculoReceita.calculaDescontoIrrf(salarioBase - descontoInss)
        return FeriasVO(salario = salarioBase.roundUp(), terco = calculaUmTerco(salarioBase).roundUp(), inss = descontoInss.roundUp(), irrf = descontoIrrf.roundUp()).apply {
            this.total = ((salarioBase + terco) - (inss + irrf)).roundUp()
        }
    }

    private fun calculaFeriasPecuniario(salario: Double) : FeriasVO{
        val salarioParcial = (salario / 30) * 20
        val salarioBase = calculaSalarioBase(salarioParcial)
        val descontoInss = CalculoReceita.calculaDescontoInss(salarioBase)
        val descontoIrrf = CalculoReceita.calculaDescontoIrrf(salarioBase - descontoInss)
        return FeriasVO(salario = salarioParcial.roundUp(), terco = calculaUmTerco(salarioParcial).roundUp(), inss = descontoInss.roundUp(), irrf = descontoIrrf.roundUp()).apply {
            this.total = ((salarioParcial + terco) - (inss + irrf)).roundUp()
        }
    }

    private fun calculaFeriasFracionada(salario: Double,dias : Int) : FeriasVO{
        val salarioParcial = (salario / 30) * dias
        val salarioBase = calculaSalarioBase(salarioParcial)
        val descontoInss = CalculoReceita.calculaDescontoInss(salarioBase)
        val descontoIrrf = CalculoReceita.calculaDescontoIrrf(salarioBase - descontoInss)
        return FeriasVO(salario = salarioParcial.roundUp(), terco = calculaUmTerco(salarioParcial).roundUp(), inss = descontoInss.roundUp(), irrf = descontoIrrf.roundUp()).apply {
            this.total = ((salarioParcial + terco) - (inss + irrf)).roundUp()
        }
    }

    private fun calculaSalarioBase(salarioParcial : Double) : Double{
        val umTerco = calculaUmTerco(salarioParcial)
        return salarioParcial.plus(umTerco)
    }

    private fun calculaUmTerco(salario : Double) = salario / 3

}