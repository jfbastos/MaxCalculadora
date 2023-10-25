package com.zamfir.maxcalculadora.domain.usecase

import android.util.Log
import com.zamfir.maxcalculadora.data.model.Trimestre
import com.zamfir.maxcalculadora.data.repository.TrimestreRepository
import com.zamfir.maxcalculadora.util.UtilData
import com.zamfir.maxcalculadora.util.convertMonetaryToDouble
import com.zamfir.maxcalculadora.util.convertToPercent
import kotlin.jvm.Throws

class TrimestreUseCase(private val repository: TrimestreRepository) {

    @Throws
    operator fun invoke(isCalculoParcial : Boolean, valorPrimeiroTri : String, valorSegundoTri : String, valorTerceiroTri : String, valorQuartoTri : String, valorMeta : String, dataAdmissao : String) : Double{
        val trimestre = Trimestre(
            valorPrimeiroTrimestre = valorPrimeiroTri.convertMonetaryToDouble(),
            valorSegundoTrimestre = valorSegundoTri.convertMonetaryToDouble(),
            valorTerceiroTrimestre = valorTerceiroTri.convertMonetaryToDouble(),
            valorQuartoTrimestre = valorQuartoTri.convertMonetaryToDouble(),
            metaAtingida = valorMeta.convertToPercent(),
            isCalculoParcial = isCalculoParcial,
            dataAdmissao = dataAdmissao
        )

        return calculaBonificacao(trimestre)
    }

    private fun calculaBonificacao(trimestre: Trimestre) : Double{
        val salarioBase = calculaSalarioBase(trimestre.isCalculoParcial, trimestre.dataAdmissao)

        val baseComBonificacao = salarioBase * trimestre.metaAtingida

        val totalDescontoMesesAnteriores = trimestre.valorPrimeiroTrimestre + trimestre.valorSegundoTrimestre + trimestre.valorTerceiroTrimestre + trimestre.valorQuartoTrimestre

        return baseComBonificacao - totalDescontoMesesAnteriores
    }

    private fun calculaSalarioBase(isCalculoParcial : Boolean, mesAdmissao : String) : Double{
        try{
            val salario = repository.getSalary().convertMonetaryToDouble()
            if(!isCalculoParcial) return salario

            val mesesTrabalhados = 12 - UtilData.getMesAdmissao(mesAdmissao)

           return (salario / 12) * mesesTrabalhados

        }catch (e : Exception){
            Log.d("TrimestreUseCase", "Erro : ${e.stackTraceToString()}")
            return 0.0
        }
    }


/*    fun obterDadosSalvos() : Trimestre{
        //TODO
    }*/
}