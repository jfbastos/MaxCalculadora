package com.zamfir.maxcalculadora.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zamfir.maxcalculadora.R
import com.zamfir.maxcalculadora.databinding.FragmentMetaBinding
import com.zamfir.maxcalculadora.view.activity.MainActivity

class FragmentMeta : Fragment() {

    private lateinit var binding : FragmentMetaBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMetaBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as MainActivity).toolbar?.title = "Meta (Dev)"
    }

}