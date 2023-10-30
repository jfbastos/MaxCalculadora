package com.zamfir.maxcalculadora.domain.usecase

import android.util.Log
import com.zamfir.maxcalculadora.data.model.Meta
import com.zamfir.maxcalculadora.data.model.Usuario
import com.zamfir.maxcalculadora.data.repository.UserRepository
import com.zamfir.maxcalculadora.domain.exception.MetaException
import com.zamfir.maxcalculadora.domain.mapper.MetaMapper
import com.zamfir.maxcalculadora.domain.model.MetaVO
import com.zamfir.maxcalculadora.util.UtilData

class AnualUseCase(private val userRepository: UserRepository) {

    @Throws
    operator fun invoke(metaVO: MetaVO) : Double{
        isCamposValidos(metaVO)

        val user = userRepository.getUsuario()
        val meta = MetaMapper.voToMeta(metaVO)
        val salarioBase = calculaSalarioBase(user, meta)
        return salarioBase * meta.metaAcancada
    }

    private fun isCamposValidos(metaVO: MetaVO){
        if(metaVO.metaAcancada.isBlank()) throw MetaException("Campo de meta não pode ser vazio.")
        if(metaVO.isCalculoParcial && metaVO.mesAdmissao.isBlank()) throw MetaException("Mês de admissão para calculo parcial não pode ser vazio.")
        if(metaVO.metaAcancada.toDoubleOrNull() != null && metaVO.metaAcancada.toDouble() == 0.0) throw MetaException("Sem meta para calcular.")
    }

    private fun calculaSalarioBase( user: Usuario, meta : Meta) : Double{
        return try{
            if(meta.isCalculoParcial){
                (user.salario / 12) * UtilData.getMesAdmissao(meta.mesAdmissao)
            }else{
                user.salario
            }
        }catch (e : Exception){
            Log.d("DEBUG", "Erro : ${e.stackTraceToString()}")
            0.0
        }
    }


}