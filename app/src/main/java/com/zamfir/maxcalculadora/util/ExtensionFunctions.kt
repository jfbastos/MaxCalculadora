package com.zamfir.maxcalculadora.util

import android.opengl.Visibility
import android.view.View

fun View.show(isVisible : Boolean){
    if(isVisible) this.visibility = View.VISIBLE
    else this.visibility = View.GONE
}