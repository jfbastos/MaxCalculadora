package com.zamfir.maxcalculadora.domain.usecase

import android.util.Log
import com.zamfir.maxcalculadora.data.model.Trimestre
import com.zamfir.maxcalculadora.data.repository.TrimestreRepository
import com.zamfir.maxcalculadora.util.UtilData
import com.zamfir.maxcalculadora.util.convertMonetaryToDouble
import com.zamfir.maxcalculadora.util.convertToPercent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlin.jvm.Throws

class TrimestreUseCase(private val repository: TrimestreRepository, private val dispatcher: CoroutineDispatcher) {

    @Throws
    suspend operator fun invoke(
        isCalculoParcial : Boolean,
        valorPrimeiroTri : String,
        valorSegundoTri : String,
        valorTerceiroTri : String,
        valorQuartoTri : String,
        valorMeta : String,
        dataAdmissao : String
    ) : Double = withContext(dispatcher){
        val trimestre = Trimestre(
            valorPrimeiroTrimestre = valorPrimeiroTri.convertMonetaryToDouble(),
            valorSegundoTrimestre = valorSegundoTri.convertMonetaryToDouble(),
            valorTerceiroTrimestre = valorTerceiroTri.convertMonetaryToDouble(),
            valorQuartoTrimestre = valorQuartoTri.convertMonetaryToDouble(),
            metaAtingida = valorMeta.convertToPercent(),
            isCalculoParcial = isCalculoParcial,
            dataAdmissao = dataAdmissao
        )

        repository.saveTrimestre(trimestre)

        val result = calculaBonificacao(trimestre)

        repository.saveHistoricResult(result)

        return@withContext result
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
            return if(isCalculoParcial){
                (salario / 12) * (UtilData.getMesesTrabalhadosPorTrimestre() - UtilData.getMesAdmissao(mesAdmissao))
            }else{
                (salario / 12) * UtilData.getMesesTrabalhadosPorTrimestre()
            }
        }catch (e : Exception){
            Log.d("TrimestreUseCase", "Erro : ${e.stackTraceToString()}")
            return 0.0
        }
    }


    suspend fun obterDadosSalvos() : Trimestre? = withContext(dispatcher){
      repository.getUltimoValor()
    }
}