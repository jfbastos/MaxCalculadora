package com.zamfir.maxcalculadora.view.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.zamfir.maxcalculadora.R
import com.zamfir.maxcalculadora.data.model.Usuario
import com.zamfir.maxcalculadora.databinding.FragmentFeriasBinding
import com.zamfir.maxcalculadora.domain.exception.AbonoPecuniarioException
import com.zamfir.maxcalculadora.domain.exception.QuantidadeDiasException
import com.zamfir.maxcalculadora.util.Constants
import com.zamfir.maxcalculadora.util.doubleToMonetary
import com.zamfir.maxcalculadora.util.doubleToStringWithTwoDecimals
import com.zamfir.maxcalculadora.util.setMonetary
import com.zamfir.maxcalculadora.util.show
import com.zamfir.maxcalculadora.view.activity.MainActivity
import com.zamfir.maxcalculadora.view.listener.UserEditListener
import com.zamfir.maxcalculadora.viewmodel.FeriasViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentFerias : Fragment() {

    private lateinit var binding: FragmentFeriasBinding
    private var salario : String? = ""

    private val viewModel: FeriasViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFeriasBinding.inflate(inflater)
        salario = arguments?.getString(Constants.BUNDLE_SALARY_KEY, "")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as MainActivity).toolbar?.title = getString(R.string.toolbar_title_ferias)

        binding.txtFieldSalario.setMonetary(salario ?: "")

        viewModel.feriasState.observe(viewLifecycleOwner) { state ->
            state.result?.let { ferias ->
                binding.resultadoPlaceHolder.show(true)
                binding.salario.text = ferias.salario.doubleToMonetary()
                binding.umTerco.text = ferias.terco.doubleToMonetary()
                binding.inss.text = ferias.inss.doubleToMonetary()
                binding.irrf.text = ferias.irrf.doubleToMonetary()

                if(ferias.pecuniario != 0.0) {
                    binding.abonoPlaceHolder.show(true)
                    binding.abono.text = ferias.pecuniario.doubleToMonetary()
                } else {
                    binding.abonoPlaceHolder.show(false)
                }

                if(ferias.tercoPecuniario != 0.0) {
                    binding.tercoAbonoPlaceHolder.show(true)
                    binding.umTercoAbono.text = ferias.tercoPecuniario.doubleToMonetary()
                } else {
                    binding.tercoAbonoPlaceHolder.show(false)
                }

                if(ferias.adiantDecimo != 0.0) {
                    binding.adiantamentoPlaceHolder.show(true)
                    binding.decimo.text = ferias.adiantDecimo.doubleToMonetary()
                } else {
                    binding.adiantamentoPlaceHolder.show(false)
                }

                binding.total.text = ferias.total.doubleToMonetary()
            }

            state.error?.let {
                when(it){
                    is QuantidadeDiasException -> Snackbar.make(requireView(), "${it.message}", Snackbar.LENGTH_LONG).show()
                    is AbonoPecuniarioException -> {
                        AlertDialog.Builder(requireContext())
                            .setTitle("Abono pecuniário")
                            .setMessage(it.message)
                            .setPositiveButton(getString(R.string.ok)) { dialog, _ ->
                                dialog.dismiss()
                            }.show()
                    }
                }
            }
        }

        binding.btnCalcular.setOnClickListener {
            viewModel.calculaFerias(diasFerias = getDiasFerias(), isAbono = binding.isAbono.isChecked, isAdiantamento = binding.isAdiantamentoDecimo.isChecked)
        }

        UserEditListener.onReceiver(object : UserEditListener {
            override fun onUpdateUser(usuario: Usuario) {
                binding.txtFieldSalario.setMonetary(usuario.salario.doubleToStringWithTwoDecimals())
                (requireActivity() as MainActivity).setHeaderValues(salario = "${binding.txtFieldSalario.text}", nome = usuario.nome)
            }

            override fun onError(e: Exception) {
                Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getDiasFerias() = binding.txtFieldDiasFerias.text.toString().takeIf { it.isNotBlank() }?.toInt() ?: 30

}