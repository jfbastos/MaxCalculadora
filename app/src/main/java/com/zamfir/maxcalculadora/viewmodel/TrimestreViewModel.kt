package com.zamfir.maxcalculadora.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zamfir.maxcalculadora.data.model.Trimestre
import com.zamfir.maxcalculadora.domain.model.TrimestreVO
import com.zamfir.maxcalculadora.domain.usecase.TrimestreUseCase
import com.zamfir.maxcalculadora.viewmodel.state.TrimestralState
import kotlinx.coroutines.launch

class TrimestreViewModel(private val trimestreUseCase : TrimestreUseCase) : ViewModel() {

    private var _trimestralState = MutableLiveData<TrimestralState>()
    val trimestralState : LiveData<TrimestralState> get() = _trimestralState

    private val _ultimoCalculo = MutableLiveData<Trimestre?>()
    val ultimoCalculo : LiveData<Trimestre?> get() = _ultimoCalculo


    fun getBonificacaoTrimestral(trimestre: TrimestreVO) = viewModelScope.launch {
            try{
                _trimestralState.value = TrimestralState(isLoading = true)
                _trimestralState.value = TrimestralState(result = trimestreUseCase(trimestre))
            }catch (e : Exception){
                _trimestralState.value = TrimestralState(error = e)
            }
    }

    fun getUltimoValor() = viewModelScope.launch {
        _ultimoCalculo.value = trimestreUseCase.obterDadosSalvos()
    }


}