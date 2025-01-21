package com.zamfir.maxcalculadora.domain.usecase

import com.zamfir.maxcalculadora.data.model.Usuario
import com.zamfir.maxcalculadora.data.repository.UserRepository
import com.zamfir.maxcalculadora.domain.model.Either
import com.zamfir.maxcalculadora.domain.util.ConstantesMensagens
import com.zamfir.maxcalculadora.util.Constants
import com.zamfir.maxcalculadora.util.convertMonetaryToDouble

class UserUseCase(private val userRespository : UserRepository) {

    fun salvarUsuario(salario : String, nome : String) : Either<String, Usuario> {
        return try{
            if(salario.isBlank()) {
                return Either.Left(ConstantesMensagens.MSG_SALARIO_VAZIO)
            }

            if(salario.convertMonetaryToDouble() < Constants.SALARIO_MINIMO){
                return Either.Left(ConstantesMensagens.MSG_SALARIO_MENOR_QUE_MINIMO)
            }

            if(nome.isBlank() || nome.trim().length < 2){
                return Either.Left(ConstantesMensagens.MSG_NOME_VAZIO)
            }

            Either.Right(userRespository.saveShared(salario, nome))
        }catch (e : Exception){
            Either.Left(e.stackTraceToString())
        }
    }

    operator fun invoke() : Either<String, Usuario> {
        return try{
            val usuario = userRespository.getUsuario()
            Either.Right(usuario)
        }catch (e : Exception){
            Either.Left(e.stackTraceToString())
        }

    }
}