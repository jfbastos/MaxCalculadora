package com.zamfir.maxcalculadora.view.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.zamfir.maxcalculadora.R
import com.zamfir.maxcalculadora.databinding.FragmentMetaBinding
import com.zamfir.maxcalculadora.domain.model.MetaVO
import com.zamfir.maxcalculadora.util.doubleToStringWithTwoDecimals
import com.zamfir.maxcalculadora.util.show
import com.zamfir.maxcalculadora.view.activity.MainActivity
import com.zamfir.maxcalculadora.viewmodel.AnualViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentMeta : Fragment() {

    private lateinit var binding : FragmentMetaBinding

    private var salario : String? = ""

    private val viewModel : AnualViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMetaBinding.inflate(inflater)
        salario = arguments?.getString("salario", "")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as MainActivity).toolbar?.title = "Meta anual"

        setTextFields()

        setCalculoParcial()

        viewModel.anualState.observe(viewLifecycleOwner){ anualState ->
            anualState.result?.let {
                val bonificacao = "R$ ${anualState.result.doubleToStringWithTwoDecimals()}"
                binding.resultadoPlaceHolder.show(true)
                binding.txvValorBonificacao.text = bonificacao
            }

            anualState.error?.let {
                Snackbar.make(requireView(), "${it.message}", Snackbar.LENGTH_SHORT).show()
            }
        }

        binding.btnCalcular.setOnClickListener {
            viewModel.calculaBonificacaoAnual(MetaVO(mesAdmissao = binding.exposedMesAdmissao.text.toString(), metaAcancada = binding.txtFieldMeta.text.toString(), isCalculoParcial = binding.isCalculoParcial.isChecked))
        }
    }

    private fun setTextFields() {
        binding.txtFieldSalario.setText(salario ?: "vazio")
    }

    private fun setCalculoParcial() {

        binding.isCalculoParcial.setOnCheckedChangeListener { _, isOn ->
            binding.layoutMesAdmissao.show(isOn)
        }

        binding.infoCalculoParcialBtn.setOnClickListener {
            Snackbar.make(requireView(), "Calculo feito em cima da data de admissão.", Snackbar.LENGTH_SHORT).setAction("Saiba mais") {
                AlertDialog.Builder(requireContext()).setTitle("Meta parcial").setMessage(getString(R.string.info_calculo_parcial)).setPositiveButton("Ok") { dialog, _ ->
                    dialog.dismiss()
                }.show()
            }.show()
        }
    }
}