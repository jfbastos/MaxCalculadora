package com.zamfir.maxcalculadora.domain.usecase

import com.zamfir.maxcalculadora.data.model.Meta
import com.zamfir.maxcalculadora.data.repository.UserRepository
import com.zamfir.maxcalculadora.domain.model.Either
import com.zamfir.maxcalculadora.domain.mapper.MetaMapper
import com.zamfir.maxcalculadora.domain.model.MetaVO
import com.zamfir.maxcalculadora.domain.util.ConstantesMensagens.MSG_CAMPO_META_VAZIO
import com.zamfir.maxcalculadora.domain.util.ConstantesMensagens.MSG_MES_ADMISSAO_VAZIO
import com.zamfir.maxcalculadora.domain.util.ConstantesMensagens.MSG_SEM_META
import com.zamfir.maxcalculadora.util.UtilData

class AnualUseCase(private val userRepository: UserRepository) {

    @Throws
    operator fun invoke(metaVO: MetaVO) : Either<String, Double> {
        if(metaVO.metaAcancada.isBlank()) return Either.Left(MSG_CAMPO_META_VAZIO)
        if(metaVO.isCalculoParcial && metaVO.mesAdmissao.isBlank()) return Either.Left(MSG_MES_ADMISSAO_VAZIO)
        if(metaVO.metaAcancada.toDoubleOrNull() != null && metaVO.metaAcancada.toDouble() == 0.0) return Either.Left(MSG_SEM_META)

        val meta = MetaMapper.voToMeta(metaVO)
        val salarioBase = calculaSalarioBase(meta)
        return Either.Right(salarioBase * meta.metaAcancada)
    }


    private fun calculaSalarioBase(meta : Meta) : Double{
        return try{
            val salario = userRepository.getUsuario().salario
            if(meta.isCalculoParcial){
                (salario / 12) * (12 - UtilData.getMesAdmissao(meta.mesAdmissao))
            }else{
                (salario / 12) * 12
            }
        }catch (e : Exception){
            e.printStackTrace()
            0.0
        }
    }


}