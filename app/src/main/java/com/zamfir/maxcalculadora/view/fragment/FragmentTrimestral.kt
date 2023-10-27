package com.zamfir.maxcalculadora.view.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.zamfir.maxcalculadora.R
import com.zamfir.maxcalculadora.data.model.Trimestre
import com.zamfir.maxcalculadora.databinding.FragmentTrimestralBinding
import com.zamfir.maxcalculadora.util.doubleToStringWithTwoDecimals
import com.zamfir.maxcalculadora.util.setMonetary
import com.zamfir.maxcalculadora.util.show
import com.zamfir.maxcalculadora.view.activity.MainActivity
import com.zamfir.maxcalculadora.viewmodel.TrimestreViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

class FragmentTrimestral : Fragment() {

    private lateinit var binding: FragmentTrimestralBinding
    private var salario : String? = ""
    private var trimestre : Trimestre? = null

    private val viewModel : TrimestreViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentTrimestralBinding.inflate(inflater)

        salario = arguments?.getString("salario", "")

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as MainActivity).toolbar?.title = "Trimestral"
        if((requireActivity() as MainActivity).toolbar?.navigationIcon == null){
            (requireActivity() as MainActivity).configNavigationDrawer()
        }

        viewModel.getUltimoValor()

        viewModel.ultimoCalculo.observe(viewLifecycleOwner) { trimestre ->
            this.trimestre = trimestre
            setTextFields(trimestre)
        }

        setCalculoParcial()

        viewModel.trimestralState.observe(viewLifecycleOwner){ state ->
            state.result?.let {
                val bonificacao = "R$ ${state.result.doubleToStringWithTwoDecimals()}"
                binding.resultadoPlaceHolder.show(true)
                binding.txvValorBonificacao.text = bonificacao
            }
        }

        binding.btnCalcular.setOnClickListener {
            if(binding.txtFieldMeta.text.toString().isBlank()) {
                Snackbar.make(requireView(), "Valor da meta não pode ser vazia.", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(binding.txtFieldMeta.text.toString() == "0"){
                Snackbar.make(requireView(), "Sem meta para calcular.", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.getBonificacaoTrimestral(
                binding.isCalculoParcial.isChecked,
                binding.txtFieldPrimeiroTri.text.toString(),
                binding.txtFieldSegundoTri.text.toString(),
                binding.txtFieldTerceiroTri.text.toString(),
                binding.txtFieldQuartoTri.text.toString(),
                binding.txtFieldMeta.text.toString(),
                binding.exposedMesAdmissao.text.toString())
        }
    }

    private fun setCalculoParcial() {
        binding.isCalculoParcial.isChecked = trimestre?.isCalculoParcial == true

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

    private fun setTextFields(trimestre: Trimestre?) {
        binding.txtFieldSalario.setText(salario ?: "vazio")
        binding.txtFieldPrimeiroTri.setMonetary(trimestre?.valorPrimeiroTrimestre.doubleToStringWithTwoDecimals())
        binding.txtFieldSegundoTri.setMonetary(trimestre?.valorSegundoTrimestre.doubleToStringWithTwoDecimals())
        binding.txtFieldTerceiroTri.setMonetary(trimestre?.valorTerceiroTrimestre.doubleToStringWithTwoDecimals())
        binding.txtFieldQuartoTri.setMonetary(trimestre?.valorQuartoTrimestre.doubleToStringWithTwoDecimals())
        if(trimestre?.metaAtingida == null) binding.txtFieldMeta.setText("") else binding.txtFieldMeta.setText(String.format("%.2f", (trimestre.metaAtingida * 100)))

    }
}