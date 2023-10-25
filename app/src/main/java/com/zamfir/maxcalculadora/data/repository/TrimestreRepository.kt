package com.zamfir.maxcalculadora.data.repository

import android.content.Context
import com.zamfir.maxcalculadora.data.AppDatabase
import com.zamfir.maxcalculadora.util.Constants
import kotlinx.coroutines.CoroutineDispatcher


class TrimestreRepository(private val context: Context ,private val appdataBase : AppDatabase, private val dispatcher: CoroutineDispatcher){

    fun getSalary() : String{
        val sharedPreferences = context.getSharedPreferences(Constants.SHARED_FILE, Context.MODE_PRIVATE)
        return sharedPreferences.getString(Constants.SHARED_SALARY_KEY, "") ?: ""
    }


}