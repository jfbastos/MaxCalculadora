package com.zamfir.maxcalculadora.data.repository

import android.content.Context
import com.zamfir.maxcalculadora.data.AppDatabase
import com.zamfir.maxcalculadora.data.TipoHistorico
import com.zamfir.maxcalculadora.data.model.Historico
import com.zamfir.maxcalculadora.data.model.Trimestre
import com.zamfir.maxcalculadora.util.Constants
import com.zamfir.maxcalculadora.util.doubleToStringWithTwoDecimals
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext


class TrimestreRepository(private val context: Context ,private val appdataBase : AppDatabase, private val dispatcher: CoroutineDispatcher){

    fun getSalary() : String{
        val sharedPreferences = context.getSharedPreferences(Constants.SHARED_FILE, Context.MODE_PRIVATE)
        return sharedPreferences.getString(Constants.SHARED_SALARY_KEY, "") ?: ""
    }


    suspend fun saveTrimestre(trimestre: Trimestre) = withContext(dispatcher){
        appdataBase.trimestreDao().insert(trimestre)
    }

    suspend fun saveHistoricResult(result : Double) = withContext(dispatcher){
        try{
            if(appdataBase.historicoDao().getAll().last().valor != result.doubleToStringWithTwoDecimals()){
                appdataBase.historicoDao().insert(Historico(TipoHistorico.TRIMESTRE.id, result.doubleToStringWithTwoDecimals()))
            }
        }catch (e : NoSuchElementException){
            appdataBase.historicoDao().insert(Historico(TipoHistorico.TRIMESTRE.id, result.doubleToStringWithTwoDecimals()))
        }
    }

    suspend fun getUltimoValor() : Trimestre? = withContext(dispatcher) {
        try{
            appdataBase.trimestreDao().getAll().last()
        }catch (e : NoSuchElementException){
            null
        }
    }


}