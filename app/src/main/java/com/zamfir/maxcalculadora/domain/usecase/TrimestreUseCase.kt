package com.zamfir.maxcalculadora.domain.usecase

import com.zamfir.maxcalculadora.data.model.Trimestre
import com.zamfir.maxcalculadora.data.repository.TrimestreRepository
import com.zamfir.maxcalculadora.domain.model.Either
import com.zamfir.maxcalculadora.domain.mapper.TrimestreMapper
import com.zamfir.maxcalculadora.domain.model.TrimestreVO
import com.zamfir.maxcalculadora.domain.util.ConstantesMensagens.MSG_MES_CALCULO_PARCIAL_VAZIO
import com.zamfir.maxcalculadora.domain.util.ConstantesMensagens.MSG_META_ATINGIDA_ACIMA_100
import com.zamfir.maxcalculadora.domain.util.ConstantesMensagens.MSG_META_VAZIA
import com.zamfir.maxcalculadora.domain.util.ConstantesMensagens.MSG_SEM_META
import com.zamfir.maxcalculadora.domain.util.ConstantesMensagens.MSG_VALOR_PRIMEIRO_TRIMESTRE_INVALIDO
import com.zamfir.maxcalculadora.domain.util.ConstantesMensagens.MSG_VALOR_SEGUNDO_TRIMESTRE_INVALIDO
import com.zamfir.maxcalculadora.domain.util.ConstantesMensagens.MSG_VALOR_TERCEIRO_TRIMESTRE_INVALIDO
import com.zamfir.maxcalculadora.util.UtilData
import com.zamfir.maxcalculadora.util.convertMonetaryToDouble
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class TrimestreUseCase(private val repository: TrimestreRepository, private val dispatcher: CoroutineDispatcher) {

    @Throws
    suspend operator fun invoke(trimestreVo: TrimestreVO): Either<String, Double> {

        val salario = repository.getSalary().convertMonetaryToDouble()

        if(trimestreVo.metaAtingida.isBlank()) return Either.Left(MSG_META_VAZIA)
        if(trimestreVo.isCalculoParcial && trimestreVo.dataAdmissao.isBlank()) return Either.Left(MSG_MES_CALCULO_PARCIAL_VAZIO)
        if(trimestreVo.metaAtingida.toDoubleOrNull() != null && trimestreVo.metaAtingida.toDouble() == 0.0) return Either.Left(MSG_SEM_META)
        if(trimestreVo.valorPrimeiroTrimestre.convertMonetaryToDouble() >= salario * 0.25) return Either.Left(MSG_VALOR_PRIMEIRO_TRIMESTRE_INVALIDO)
        if(trimestreVo.valorSegundoTrimestre.convertMonetaryToDouble() >= salario * 0.50) return Either.Left(MSG_VALOR_SEGUNDO_TRIMESTRE_INVALIDO)
        if(trimestreVo.valorTerceiroTrimestre.convertMonetaryToDouble() >= salario * 0.75) return Either.Left(MSG_VALOR_TERCEIRO_TRIMESTRE_INVALIDO)
        trimestreVo.metaAtingida.toDoubleOrNull()?.let {
            if(it > 100.0) return Either.Left(MSG_META_ATINGIDA_ACIMA_100)
        }

        val trimestre = TrimestreMapper.voToModel(trimestreVo)

        repository.saveTrimestre(trimestre)

        return Either.Right(calculaBonificacao(trimestre))
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
            e.printStackTrace()
            0.0
        }
    }

    suspend fun obterDadosSalvos() : Trimestre? = withContext(dispatcher){
      repository.getUltimoValor()
    }
}