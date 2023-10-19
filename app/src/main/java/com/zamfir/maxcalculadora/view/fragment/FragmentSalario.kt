package com.zamfir.maxcalculadora.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zamfir.maxcalculadora.databinding.FragmentSalarioBinding
import com.zamfir.maxcalculadora.view.activity.MainActivity

class FragmentSalario : Fragment() {

    private lateinit var binding : FragmentSalarioBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSalarioBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as MainActivity).toolbar?.title = "Salário"
    }

}