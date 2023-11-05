package com.zamfir.maxcalculadora.view.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.zamfir.maxcalculadora.data.model.Usuario
import com.zamfir.maxcalculadora.databinding.EditUserBottomSheetBinding
import com.zamfir.maxcalculadora.util.convertMonetaryToDouble
import com.zamfir.maxcalculadora.util.setMonetary
import com.zamfir.maxcalculadora.view.listener.UserEditListener

class EditUserBottomSheet(private val callback: (String, String) -> Unit) : BottomSheetDialogFragment() {

    private lateinit var binding: EditUserBottomSheetBinding
    private var salario = ""
    private var nome = ""

    companion object {
        const val TAG = "ModalBottomSheet"

        fun newInstance(salario : String, nome : String, callback : (String, String) -> Unit) : EditUserBottomSheet{
            val editUserBottomSheet = EditUserBottomSheet(callback)
            editUserBottomSheet.salario = salario
            editUserBottomSheet.nome = nome
            return editUserBottomSheet
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = EditUserBottomSheetBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.txtFieldNome.setText(nome)
        binding.txtFieldSalario.setMonetary(salario)

        binding.botaoSalvar.setOnClickListener {
            callback.invoke(binding.txtFieldSalario.text.toString(), binding.txtFieldNome.text.toString())
            UserEditListener.initListener.onUpdateUser(usuario = Usuario(binding.txtFieldSalario.text.toString().convertMonetaryToDouble(), binding.txtFieldNome.text.toString()))
            dismiss()
        }

        binding.closeBottomSheet.setOnClickListener {
            dismiss()
        }
    }
}