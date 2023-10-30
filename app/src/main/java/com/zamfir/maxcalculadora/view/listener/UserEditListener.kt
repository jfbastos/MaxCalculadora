package com.zamfir.maxcalculadora.view.listener

import com.zamfir.maxcalculadora.data.model.Usuario

interface UserEditListener {
    fun onUpdateUser(usuario : Usuario)
    fun onError(e : Exception)

    companion object {
        lateinit var initListener : UserEditListener
        fun onReceiver(userEditListener: UserEditListener){
            initListener = userEditListener
        }
    }
}