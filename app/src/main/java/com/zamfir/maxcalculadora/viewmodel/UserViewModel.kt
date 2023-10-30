package com.zamfir.maxcalculadora.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zamfir.maxcalculadora.domain.usecase.UserUseCase
import com.zamfir.maxcalculadora.viewmodel.state.UserState
import kotlinx.coroutines.launch

class UserViewModel(private val userCase : UserUseCase) : ViewModel(){

    private val _userSate = MutableLiveData<UserState>()
    val userState : LiveData<UserState> get() = _userSate

    private val _editUserState = MutableLiveData<UserState>()
    val editUserState : LiveData<UserState> get() = _editUserState


    fun salvarDadosUsuario(salario : String, nome : String) = viewModelScope.launch {
        try{
            _userSate.value = UserState(isLoading = true)
            _userSate.value = UserState(usuario = userCase.salvarUsuario(salario, nome))
        }catch (e : Exception){
            _userSate.value = UserState(error = e)
        }
    }

    fun getDadosUsuario() = viewModelScope.launch {
        _editUserState.value = UserState(usuario = userCase())
    }



}