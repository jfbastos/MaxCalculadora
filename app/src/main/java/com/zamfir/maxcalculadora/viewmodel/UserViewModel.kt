package com.zamfir.maxcalculadora.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zamfir.maxcalculadora.domain.model.Either
import com.zamfir.maxcalculadora.domain.usecase.UserUseCase
import com.zamfir.maxcalculadora.viewmodel.state.UserState
import kotlinx.coroutines.launch

class UserViewModel(private val userCase : UserUseCase) : ViewModel(){

    private val _userSate = MutableLiveData<UserState>()
    val userState : LiveData<UserState> get() = _userSate

    fun salvarDadosUsuario(salario : String, nome : String) = viewModelScope.launch {
        _userSate.value = when(val resultado = userCase.salvarUsuario(salario, nome)){
            is Either.Left -> UserState(error = resultado.value)
            is Either.Right -> UserState(usuario = resultado.value)
        }
    }
}