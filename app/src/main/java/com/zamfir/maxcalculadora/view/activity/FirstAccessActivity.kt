package com.zamfir.maxcalculadora.view.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.color.MaterialColors
import com.google.android.material.snackbar.Snackbar
import com.zamfir.maxcalculadora.databinding.ActivityFirstAccessBinding
import com.zamfir.maxcalculadora.util.Constants
import com.zamfir.maxcalculadora.util.doubleToMonetary
import com.zamfir.maxcalculadora.util.setMonetary
import com.zamfir.maxcalculadora.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FirstAccessActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFirstAccessBinding

    private val viewModel: UserViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstAccessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fabNext.setOnClickListener {
            viewModel.salvarDadosUsuario(binding.salario.text.toString(), binding.nome.text.toString())
        }

        viewModel.userState.observe(this) { userState ->

            if(userState.error.isNotBlank()){
                Snackbar.make(binding.root, userState.error, Snackbar.LENGTH_SHORT).setBackgroundTint(Color.WHITE).setTextColor(MaterialColors.getColor(this, androidx.appcompat.R.attr.colorPrimary, Color.BLACK)).show()
                return@observe
            }

            userState.usuario?.let {
                val bundle = Bundle().apply {
                    putString(Constants.SHARED_NAME_KEY, binding.nome.text.toString())
                    putString(Constants.SHARED_SALARY_KEY, binding.salario.text.toString())
                }
                setResult(RESULT_OK, Intent().putExtra("bundle", bundle))
                finish()
            }
        }

        binding.salario.setMonetary((0.0).doubleToMonetary())
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        this.finishAffinity()

    }
}