package com.zamfir.maxcalculadora.view.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.zamfir.maxcalculadora.databinding.FragmentTrimestralBinding
import com.zamfir.maxcalculadora.util.setMonetary
import com.zamfir.maxcalculadora.util.show
import com.zamfir.maxcalculadora.view.activity.MainActivity
import com.zamfir.maxcalculadora.viewmodel.TrimestreViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentTrimestral : Fragment() {

    private lateinit var binding: FragmentTrimestralBinding
    private var salario : String? = ""

    private val viewModel : TrimestreViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentTrimestralBinding.inflate(inflater)

        salario = arguments?.getString("salario", "")

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as MainActivity).toolbar?.title = "Trimestral"

        setTextFields()

        setCalculoParcial()

        viewModel.trimestralState.observe(viewLifecycleOwner){ state ->
            Log.d("TrimestralUI", "Result : ${state.result}")

            state.result?.let {
                Snackbar.make(requireView(), "Meta : R$ ${String.format("%.2f", state.result)}.", Snackbar.LENGTH_SHORT).show()

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
        binding.isCalculoParcial.setOnCheckedChangeListener { _, isOn ->
            binding.layoutMesAdmissao.show(isOn)
        }

        binding.infoCalculoParcialBtn.setOnClickListener {
            Snackbar.make(requireView(), "Calculo feito em cima da data de admissão.", Snackbar.LENGTH_SHORT).setAction("Saiba mais") {
                AlertDialog.Builder(requireContext()).setTitle("Meta parcial").setMessage("O cálculo da meta, será baseada em cima da data da sua admissão.\n\nSerá calculado o valor proporcial ao período.").setPositiveButton("Ok") { dialog, _ ->
                    dialog.dismiss()
                }.show()
            }.show()
        }
    }

    private fun setTextFields() {
        binding.txtFieldSalario.setText(salario ?: "vazio")
        binding.txtFieldPrimeiroTri.setMonetary("0")
        binding.txtFieldSegundoTri.setMonetary("0")
        binding.txtFieldTerceiroTri.setMonetary("0")
        binding.txtFieldQuartoTri.setMonetary("0")
    }
}