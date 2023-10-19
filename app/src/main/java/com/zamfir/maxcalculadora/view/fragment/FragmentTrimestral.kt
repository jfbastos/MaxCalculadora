package com.zamfir.maxcalculadora.view.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.zamfir.maxcalculadora.databinding.FragmentTrimestralBinding
import com.zamfir.maxcalculadora.util.show
import com.zamfir.maxcalculadora.view.activity.MainActivity
import java.util.Calendar

class FragmentTrimestral : Fragment() {

    private lateinit var binding: FragmentTrimestralBinding
    private var salario : String? = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentTrimestralBinding.inflate(inflater)

        salario = arguments?.getString("salario", "")

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as MainActivity).toolbar?.title = "Trimestral"

        binding.isCalculoParcial.setOnCheckedChangeListener { _, isOn ->
            binding.dataAdmissaoPlaceHolder.show(isOn)
            binding.datePickerDataAdmissao.show(isOn)
            binding.formPlaceHolder.show(!isOn)
        }

        binding.datePickerDataAdmissao.init(
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        ){_, year, month, day ->

            val formattedDay = if (day < 10) "0${day}" else "$day"
            val formattedMonth = if (month < 10) "0${month}" else "$month"

            val date = "$formattedDay/$formattedMonth/$year"

            binding.txvDataAdmissao.text = date
            binding.datePickerDataAdmissao.show(false)
            binding.formPlaceHolder.show(true)
        }

        binding.txtFieldSalario.setText(salario ?: "")

        binding.infoCalculoParcialBtn.setOnClickListener {
            Snackbar.make(requireView(), "Calculo feito em cima da data de admissão.", Snackbar.LENGTH_SHORT).setAction("Saiba mais"){
                AlertDialog.Builder(requireContext())
                    .setTitle("Meta parcial")
                    .setMessage("O cálculo da meta, será baseada em cima da data da sua admissão.\n\nSerá calculado o valor proporcial ao período.")
                    .setPositiveButton("Ok") {dialog , _ ->
                        dialog.dismiss()
                    }.show()
            }.show()
        }
    }
}