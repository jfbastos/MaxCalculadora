package com.zamfir.maxcalculadora.domain.usecase

import com.zamfir.maxcalculadora.data.model.Usuario
import com.zamfir.maxcalculadora.data.repository.UserRepository
import com.zamfir.maxcalculadora.domain.exception.CadastroException
import com.zamfir.maxcalculadora.util.Constants
import com.zamfir.maxcalculadora.util.convertMonetaryToDouble

class UserUseCase(private val userRespository : UserRepository) {

    fun salvarUsuario(salario : String, nome : String) : Usuario {
        isCamposValidos(salario, nome)
        return userRespository.saveShared(salario, nome)
    }

    operator fun invoke() : Usuario{
        return userRespository.getUsuario()
    }

    private fun isCamposValidos(salario: String, nome: String){
        if(salario.isBlank()) throw CadastroException("Campo salário não pode ser vazio.")
        if(salario.convertMonetaryToDouble() < Constants.SALARIO_MINIMO) throw CadastroException("Salário não pode ser menor do que um salário minimo.")
        if(nome.isBlank() || nome.trim().length < 4) throw CadastroException("Campo nome deve ter ao menos 4 caracteres.")
    }
}