package com.zamfir.maxcalculadora.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zamfir.maxcalculadora.domain.model.Either
import com.zamfir.maxcalculadora.domain.model.MetaVO
import com.zamfir.maxcalculadora.domain.usecase.AnualUseCase
import com.zamfir.maxcalculadora.viewmodel.state.AnualState
import kotlinx.coroutines.launch


class AnualViewModel(private val anualUseCase : AnualUseCase) : ViewModel(){

    private var _anualState = MutableLiveData<AnualState>()
    val anualState : LiveData<AnualState> get() = _anualState

    fun calculaBonificacaoAnual(meta : MetaVO) = viewModelScope.launch {
        when(val result = anualUseCase(meta)){
            is Either.Left -> _anualState.value = AnualState(error = result.value)
            is Either.Right -> _anualState.value = AnualState(result = result.value)
        }
    }
}