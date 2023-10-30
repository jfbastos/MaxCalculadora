package com.zamfir.maxcalculadora.data.repository

import android.content.Context
import com.zamfir.maxcalculadora.data.model.Usuario
import com.zamfir.maxcalculadora.util.Constants
import com.zamfir.maxcalculadora.util.convertMonetaryToDouble
import kotlinx.coroutines.CoroutineDispatcher

class UserRepository(private val context : Context, private val dispatcher: CoroutineDispatcher) {

    fun saveShared(salario : String, nome : String) : Usuario {
        context.getSharedPreferences(Constants.SHARED_FILE, 0).edit().apply {
            putString(Constants.SHARED_SALARY_KEY, salario)
            putString(Constants.SHARED_NAME_KEY, nome)
            apply()
        }
        return Usuario(salario.convertMonetaryToDouble(), nome)
    }

    fun getUsuario() = Usuario(getSalary().convertMonetaryToDouble(), getNome())

    private fun getSalary() : String{
        val sharedPreferences = context.getSharedPreferences(Constants.SHARED_FILE, Context.MODE_PRIVATE)
        return sharedPreferences.getString(Constants.SHARED_SALARY_KEY, "") ?: ""
    }

    private fun getNome() : String{
        val sharedPreferences = context.getSharedPreferences(Constants.SHARED_FILE, Context.MODE_PRIVATE)
        return sharedPreferences.getString(Constants.SHARED_NAME_KEY, "") ?: ""
    }
}