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
import com.zamfir.maxcalculadora.util.Constants
import com.zamfir.maxcalculadora.util.doubleToStringWithTwoDecimals
import com.zamfir.maxcalculadora.util.show
import com.zamfir.maxcalculadora.view.activity.MainActivity
import com.zamfir.maxcalculadora.viewmodel.AnualViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentMeta : Fragment() {

    private lateinit var binding : FragmentMetaBinding

    private var salario : String? = ""

    private val viewModel : AnualViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMetaBinding.inflate(inflater)
        salario = arguments?.getString(Constants.BUNDLE_SALARY_KEY, "")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as MainActivity).toolbar?.title = getString(R.string.toolbar_title_meta)

        setTextFields()

        setCalculoParcial()

        viewModel.anualState.observe(viewLifecycleOwner){ anualState ->

            if(anualState.error.isNotBlank()){
                Snackbar.make(requireView(), anualState.error, Snackbar.LENGTH_SHORT).show()
                return@observe
            }

            anualState.result?.let {
                val bonificacao = "R$ ${anualState.result.doubleToStringWithTwoDecimals()}"
                binding.resultadoPlaceHolder.show(true)
                binding.txvValorBonificacao.text = bonificacao
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
            Snackbar.make(requireView(), getString(R.string.info_snackbar_parcial), Snackbar.LENGTH_SHORT).setAction(getString(R.string.btn_saiba_mais_snackbar)) {
                AlertDialog.Builder(requireContext()).setTitle(getString(R.string.title_dialog_parcial)).setMessage(getString(R.string.info_calculo_parcial)).setPositiveButton(getString(R.string.ok)) { dialog, _ ->
                    dialog.dismiss()
                }.show()
            }.show()
        }
    }
}