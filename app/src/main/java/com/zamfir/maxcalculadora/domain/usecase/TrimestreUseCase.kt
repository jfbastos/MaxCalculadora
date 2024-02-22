package com.zamfir.maxcalculadora.domain.usecase

import com.zamfir.maxcalculadora.data.model.Trimestre
import com.zamfir.maxcalculadora.data.repository.TrimestreRepository
import com.zamfir.maxcalculadora.domain.exception.TrimestralException
import com.zamfir.maxcalculadora.domain.mapper.TrimestreMapper
import com.zamfir.maxcalculadora.domain.model.TrimestreVO
import com.zamfir.maxcalculadora.util.UtilData
import com.zamfir.maxcalculadora.util.convertMonetaryToDouble
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class TrimestreUseCase(private val repository: TrimestreRepository, private val dispatcher: CoroutineDispatcher) {

    @Throws
    suspend operator fun invoke(trimestreVo: TrimestreVO): Double {

        isTodosOsCamposValidos(trimestreVo)

        val trimestre = TrimestreMapper.voToModel(trimestreVo)

        repository.saveTrimestre(trimestre)

        return calculaBonificacao(trimestre)
    }


    private fun isTodosOsCamposValidos(trimestreVo: TrimestreVO){
        val salario = repository.getSalary().convertMonetaryToDouble()

        if(trimestreVo.metaAtingida.isBlank()) throw TrimestralException("Campo de meta atingida não pode ser vazio.")
        if(trimestreVo.isCalculoParcial && trimestreVo.dataAdmissao.isBlank()) throw TrimestralException("Mês de admissão para calculo parcial não pode ser vazio.")
        if(trimestreVo.metaAtingida.toDoubleOrNull() != null && trimestreVo.metaAtingida.toDouble() == 0.0) throw TrimestralException("Sem meta para calcular.")
        if(trimestreVo.valorPrimeiroTrimestre.convertMonetaryToDouble() >= salario * 0.25) throw TrimestralException("O valor atingido no 1º trimeste não pode ser maior do que 25% do salário total.")
        if(trimestreVo.valorSegundoTrimestre.convertMonetaryToDouble() >= salario * 0.50) throw TrimestralException("O valor atingido no 2º trimeste não pode ser maior do que 50% do salário total.")
        if(trimestreVo.valorTerceiroTrimestre.convertMonetaryToDouble() >= salario * 0.75) throw TrimestralException("O valor atingido no 3º trimeste não pode ser maior do que 75% do salário total.")
        trimestreVo.metaAtingida.toDoubleOrNull()?.let {
            if(it > 100.0) throw TrimestralException("A meta trimestral não pode ser maior que 100%")
        }
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