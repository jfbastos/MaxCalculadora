package com.zamfir.maxcalculadora.viewmodel

import androidx.lifecycle.ViewModel
import com.zamfir.maxcalculadora.domain.usecase.TrimestreUseCase

class TrimestreViewModel(private val TrimestreUseCase : TrimestreUseCase) : ViewModel() {}