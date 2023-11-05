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
import com.zamfir.maxcalculadora.data.model.Trimestre
import com.zamfir.maxcalculadora.data.model.Usuario
import com.zamfir.maxcalculadora.databinding.FragmentTrimestralBinding
import com.zamfir.maxcalculadora.domain.model.TrimestreVO
import com.zamfir.maxcalculadora.util.Constants
import com.zamfir.maxcalculadora.util.doubleToStringWithTwoDecimals
import com.zamfir.maxcalculadora.util.setMonetary
import com.zamfir.maxcalculadora.util.show
import com.zamfir.maxcalculadora.view.activity.MainActivity
import com.zamfir.maxcalculadora.view.listener.UserEditListener
import com.zamfir.maxcalculadora.viewmodel.TrimestreViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentTrimestral : Fragment() {

    private lateinit var binding: FragmentTrimestralBinding
    private var salario : String? = ""
    private var trimestre : Trimestre? = null

    private val viewModel : TrimestreViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentTrimestralBinding.inflate(inflater)
        salario = arguments?.getString(Constants.BUNDLE_SALARY_KEY, "")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as MainActivity).toolbar?.title = getString(R.string.toolbar_title_trimestral)

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

            state.error?.let {
                Snackbar.make(requireView(), "${it.message}", Snackbar.LENGTH_SHORT).show()
            }
        }

        binding.btnCalcular.setOnClickListener {
            viewModel.getBonificacaoTrimestral(setTrimestreVo())
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

    private fun setTrimestreVo() = TrimestreVO(
        isCalculoParcial = binding.isCalculoParcial.isChecked,
        valorPrimeiroTrimestre = binding.txtFieldPrimeiroTri.text.toString(),
        valorSegundoTrimestre = binding.txtFieldSegundoTri.text.toString(),
        valorTerceiroTrimestre = binding.txtFieldTerceiroTri.text.toString(),
        valorQuartoTrimestre = binding.txtFieldQuartoTri.text.toString(),
        metaAtingida = binding.txtFieldMeta.text.toString(),
        dataAdmissao = binding.exposedMesAdmissao.text.toString()
    )

    private fun setCalculoParcial() {
        binding.isCalculoParcial.isChecked = trimestre?.isCalculoParcial == true

        binding.isCalculoParcial.setOnCheckedChangeListener { _, isOn ->
            binding.layoutMesAdmissao.show(isOn)
        }

        binding.infoCalculoParcialBtn.setOnClickListener {
            Snackbar.make(requireView(),  getString(R.string.info_snackbar_parcial), Snackbar.LENGTH_SHORT).setAction(getString(R.string.btn_saiba_mais_snackbar)) {
                AlertDialog.Builder(requireContext()).setTitle(getString(R.string.title_dialog_parcial)).setMessage(getString(R.string.info_calculo_parcial)).setPositiveButton(getString(R.string.ok)) { dialog, _ ->
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