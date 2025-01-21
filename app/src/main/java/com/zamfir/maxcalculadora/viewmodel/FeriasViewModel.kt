package com.zamfir.maxcalculadora.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zamfir.maxcalculadora.domain.model.Either
import com.zamfir.maxcalculadora.domain.usecase.FeriasUseCase
import com.zamfir.maxcalculadora.viewmodel.state.FeriasState
import kotlinx.coroutines.launch

class FeriasViewModel(private val feriasUseCase: FeriasUseCase) : ViewModel() {

    private var _feriasState = MutableLiveData<FeriasState>()
    val feriasState : LiveData<FeriasState> get() = _feriasState

    fun calculaFerias(diasFerias : Int, isAbono : Boolean, isAdiantamento : Boolean) = viewModelScope.launch {
        try{
            when(val result = feriasUseCase(diasFerias, isAbono, isAdiantamento)){
                is Either.Left -> _feriasState.value = FeriasState(error = result.value)
                is Either.Right -> _feriasState.value = FeriasState(result = result.value)
            }
        }catch (e : Exception){
            _feriasState.value = FeriasState(error = e.stackTraceToString())
        }
    }
}