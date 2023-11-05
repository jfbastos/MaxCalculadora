package com.zamfir.maxcalculadora.domain.usecase

import com.zamfir.maxcalculadora.data.repository.UserRepository

class UserUseCase(private val userRespository : UserRepository) {

    fun salvarUsuario(salario : String, nome : String){
        userRespository.saveShared(salario, nome)
    }
}