package com.zamfir.maxcalculadora.view.dialog

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.color.MaterialColors
import com.google.android.material.snackbar.Snackbar
import com.zamfir.maxcalculadora.R
import com.zamfir.maxcalculadora.databinding.EditUserBottomSheetBinding
import com.zamfir.maxcalculadora.util.doubleToStringWithTwoDecimals
import com.zamfir.maxcalculadora.util.setMonetary
import com.zamfir.maxcalculadora.view.listener.UserEditListener
import com.zamfir.maxcalculadora.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditUserBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding : EditUserBottomSheetBinding

    private val viewModel : UserViewModel by viewModel()

    private var isEventBtnSalvar = false

    companion object{
        const val TAG = "ModalBottomSheet"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = EditUserBottomSheetBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getDadosUsuario()

        viewModel.editUserState.observe(viewLifecycleOwner){ editUserState ->
            editUserState.usuario?.let {
                binding.txtFieldNome.setText(it.nome)
                binding.txtFieldSalario.setMonetary(it.salario.doubleToStringWithTwoDecimals())
            }
        }

        viewModel.userState.observe(viewLifecycleOwner){ userState ->
            userState.usuario?.let {
                Snackbar.make(binding.root, getString(R.string.sucess_edit_user_msg) , Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.WHITE)
                    .setTextColor(MaterialColors.getColor(requireContext(), org.koin.android.R.attr.colorPrimary, Color.BLACK))
                    .show()
                UserEditListener.initListener.onUpdateUser(usuario = userState.usuario)
            }

            userState.error?.let {
                isEventBtnSalvar = false
                Snackbar.make(binding.root, "${it.message}" , Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.WHITE)
                    .setTextColor(MaterialColors.getColor(requireContext(), org.koin.android.R.attr.colorPrimary, Color.BLACK))
                    .show()
            }
        }

        binding.botaoSalvar.setOnClickListener {
            viewModel.salvarDadosUsuario(binding.txtFieldSalario.text.toString(),binding.txtFieldNome.text.toString())
            isEventBtnSalvar = true
        }

        binding.closeBottomSheet.setOnClickListener {
            dismiss()
        }
    }
}