package com.zamfir.maxcalculadora.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import com.zamfir.maxcalculadora.R
import com.zamfir.maxcalculadora.databinding.FirstTimeFragmentBinding
import com.zamfir.maxcalculadora.util.MoneyTextWatcher
import com.zamfir.maxcalculadora.view.activity.MainActivity
import com.zamfir.maxcalculadora.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FirstTimeFragment : Fragment() {

    private lateinit var binding : FirstTimeFragmentBinding

    private val viewModel : UserViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FirstTimeFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as MainActivity).toolbar?.title = ""

        binding.salario.apply {
            addTextChangedListener(MoneyTextWatcher(this))
            setText("0")
        }

        binding.fabNext.setOnClickListener {
            viewModel.salvarDadosUsuario(binding.salario.text.toString(), binding.nome.text.toString())
        }

        viewModel.userState.observe(viewLifecycleOwner){ userState ->
            Log.d("UserState", "User : ${userState.isSuccess} -> ${userState.error}")
            if(userState.isSuccess){
                val fragment = FragmentTrimestral()
                fragment.arguments = Bundle().apply { putString("salario", binding.salario.text.toString()) }
                requireActivity().supportFragmentManager.commit {
                    setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    replace(R.id.nav_host_fragment, fragment)
                    addToBackStack(null)
                }
            }
        }
    }
}