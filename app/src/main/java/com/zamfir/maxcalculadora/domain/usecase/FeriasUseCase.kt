package com.zamfir.maxcalculadora.domain.usecase

import com.zamfir.maxcalculadora.data.repository.UserRepository
import com.zamfir.maxcalculadora.domain.model.Either
import com.zamfir.maxcalculadora.domain.model.FeriasVO
import com.zamfir.maxcalculadora.domain.util.CalculoReceita
import com.zamfir.maxcalculadora.domain.util.ConstantesMensagens.MSG_ABONO_PECUNIARIO
import com.zamfir.maxcalculadora.domain.util.ConstantesMensagens.MSG_QNT_DIAS_MAIOR_QUE_TRINTA
import com.zamfir.maxcalculadora.domain.util.ConstantesMensagens.MSG_QNT_DIAS_MENOR_QUE_CINCO
import com.zamfir.maxcalculadora.util.roundUp

class FeriasUseCase (private val userRepository: UserRepository){

    @Throws
    operator fun invoke(diasFerias : Int, isAbono : Boolean, isAdiantamento : Boolean) : Either<String, FeriasVO> {

        when {
            diasFerias < 5 -> return Either.Left(MSG_QNT_DIAS_MENOR_QUE_CINCO)
            diasFerias > 30 -> return Either.Left(MSG_QNT_DIAS_MAIOR_QUE_TRINTA)
            diasFerias < 30 && isAbono -> return Either.Left(MSG_ABONO_PECUNIARIO)
        }

        val salario = userRepository.getUsuario().salario
        val result = if(diasFerias != 30) calculaFeriasFracionada(salario,diasFerias) else calculaFerias(salario)

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

        return Either.Right(result)
    }

    private fun calculaFerias(salario: Double) : FeriasVO{
        val salarioBase = calculaSalarioBase(salario)
        val descontoInss = CalculoReceita.calculaDescontoInss(salarioBase)
        val descontoIrrf = CalculoReceita.calculaDescontoIrrf(salarioBase - descontoInss)
        return FeriasVO(salario = salario.roundUp(), terco = calculaUmTerco(salario).roundUp(), inss = descontoInss.roundUp(), irrf = descontoIrrf.roundUp()).apply {
            this.total = ((salario + terco) - (inss + irrf)).roundUp()
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