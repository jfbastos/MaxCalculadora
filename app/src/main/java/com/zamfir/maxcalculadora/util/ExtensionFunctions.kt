package com.zamfir.maxcalculadora.util

import android.util.Log
import android.view.View
import com.google.android.material.textfield.TextInputEditText
import java.lang.StringBuilder
import java.math.RoundingMode

fun View.show(isVisible : Boolean){
    if(isVisible) this.visibility = View.VISIBLE
    else this.visibility = View.GONE
}

fun TextInputEditText.setMonetary(text : String){
    this.apply {
        addTextChangedListener(MoneyTextWatcher(this))
        setText(text)
    }
}

fun Double?.doubleToStringWithTwoDecimals() = String.format("%.2f", (this ?: 0.0))

fun Double?.doubleToMonetary() = "R$ ${String.format("%.2f", (this ?: 0.0))}"

fun Double.roundUp() = this.toBigDecimal().setScale(2, RoundingMode.UP).toDouble()

fun String?.convertMonetaryToDouble(): Double {
    if (this.isNullOrEmpty()) return 0.0
    val valueWithoutDot = removeDot(this.toString())
    return try {
        val text: String = if (valueWithoutDot.contains(',')) {
            valueWithoutDot.substring(2, valueWithoutDot.length).replace(',', '.')
        } else {
            valueWithoutDot.substring(2, valueWithoutDot.length)
        }
        text.trim().toDouble()
    } catch (e: Exception) {
        Log.e("Erro -> ConvertMonetary", "Erro ao converter para moeda. Erro : ${e.message}")
        0.0
    }
}

fun String?.convertToPercent() : Double{
    if (this.isNullOrEmpty()) return 0.0
    return try {
        val text: String = if (this.contains(',')) this.replace(',', '.') else this
        text.trim().toDouble() / 100
    }catch (e : Exception){
        Log.e("Erro -> ConvertPercent", "Erro ao converter para procentagem. Erro : ${e.message}")
        0.0
    }
}

private fun removeDot(value : String) : String{
    return StringBuilder(value).apply {
        val indexOfDot = this.indexOf(".")
        if(indexOfDot >= 0) this.deleteCharAt(indexOfDot) else this
    }.toString()
}