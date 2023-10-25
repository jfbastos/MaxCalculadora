package com.zamfir.maxcalculadora.data.repository

import android.content.Context
import com.zamfir.maxcalculadora.util.Constants
import kotlinx.coroutines.CoroutineDispatcher

class UserRepository(private val context : Context, private val dispatcher: CoroutineDispatcher) {

    fun saveShared(salario : String, nome : String){
        context.getSharedPreferences(Constants.SHARED_FILE, 0).edit().apply {
            putString(Constants.SHARED_SALARY_KEY, salario)
            putString(Constants.SHARED_NAME_KEY, nome)
            apply()
        }
    }
}