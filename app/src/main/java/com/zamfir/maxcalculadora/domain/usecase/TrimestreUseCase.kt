package com.zamfir.maxcalculadora.domain.usecase

import android.util.Log
import com.zamfir.maxcalculadora.data.model.Trimestre
import com.zamfir.maxcalculadora.data.repository.TrimestreRepository
import com.zamfir.maxcalculadora.domain.exception.TrimestralException
import com.zamfir.maxcalculadora.domain.mapper.TrimestreMapper
import com.zamfir.maxcalculadora.domain.model.TrimestreVO
import com.zamfir.maxcalculadora.util.UtilData
import com.zamfir.maxcalculadora.util.convertMonetaryToDouble
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlin.jvm.Throws

class TrimestreUseCase(private val repository: TrimestreRepository, private val dispatcher: CoroutineDispatcher) {

    @Throws
    suspend operator fun invoke(trimestreVo: TrimestreVO): Double {

        isTodosOsCamposValidos(trimestreVo)

        val trimestre = TrimestreMapper.voToModel(trimestreVo)

        repository.saveTrimestre(trimestre)

        return calculaBonificacao(trimestre)
    }


    private fun isTodosOsCamposValidos(trimestreVo: TrimestreVO){
        if(trimestreVo.metaAtingida.isBlank()) throw TrimestralException("Campo de meta atingida não pode ser vazio.")
        if(trimestreVo.isCalculoParcial && trimestreVo.dataAdmissao.isBlank()) throw TrimestralException("Mês de admissão para calculo parcial não pode ser vazio.")
        if(trimestreVo.metaAtingida.toDoubleOrNull() != null && trimestreVo.metaAtingida.toDouble() == 0.0) throw TrimestralException("Sem meta para calcular.")
    }


    private fun calculaBonificacao(trimestre: Trimestre) : Double{
        val salarioBase = calculaSalarioBase(trimestre.isCalculoParcial, trimestre.dataAdmissao)

        val baseComBonificacao = salarioBase * trimestre.metaAtingida

        val totalDescontoMesesAnteriores = trimestre.valorPrimeiroTrimestre + trimestre.valorSegundoTrimestre + trimestre.valorTerceiroTrimestre + trimestre.valorQuartoTrimestre

        return baseComBonificacao - totalDescontoMesesAnteriores
    }

    private fun calculaSalarioBase(isCalculoParcial : Boolean, mesAdmissao : String) : Double{
        return try{
            val salario = repository.getSalary().convertMonetaryToDouble()
            if(isCalculoParcial){
                (salario / 12) * (UtilData.getMesesTrabalhadosPorTrimestre() - UtilData.getMesAdmissao(mesAdmissao))
            }else{
                (salario / 12) * UtilData.getMesesTrabalhadosPorTrimestre()
            }
        }catch (e : Exception){
            Log.d("TrimestreUseCase", "Erro : ${e.stackTraceToString()}")
            0.0
        }
    }

    suspend fun obterDadosSalvos() : Trimestre? = withContext(dispatcher){
      repository.getUltimoValor()
    }
}