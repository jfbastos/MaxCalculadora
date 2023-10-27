package com.zamfir.maxcalculadora.view.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.color.MaterialColors
import com.google.android.material.snackbar.Snackbar
import com.zamfir.maxcalculadora.databinding.ActivityFirstAccessBinding
import com.zamfir.maxcalculadora.util.Constants
import com.zamfir.maxcalculadora.util.MoneyTextWatcher
import com.zamfir.maxcalculadora.util.convertMonetaryToDouble
import com.zamfir.maxcalculadora.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FirstAccessActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFirstAccessBinding

    private val viewModel : UserViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstAccessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.salario.apply {
            addTextChangedListener(MoneyTextWatcher(this))
            setText("0")
        }

        binding.fabNext.setOnClickListener {
            if(binding.salario.text.toString().convertMonetaryToDouble() < Constants.SALARIO_MINIMO){
                Snackbar.make(binding.root, "Seu salário deverá ser maior que um salário mínimo.",Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.WHITE)
                    .setTextColor(MaterialColors.getColor(this, org.koin.android.R.attr.colorPrimary, Color.BLACK))
                    .show()
                return@setOnClickListener
            }

            if(binding.nome.text.toString().length < 4){
                Snackbar.make(binding.root, "Seu nome deve ter ao menos 4 caracteres.",Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.WHITE)
                    .setTextColor(MaterialColors.getColor(this, org.koin.android.R.attr.colorPrimary, Color.BLACK))
                    .show()
                return@setOnClickListener
            }

            viewModel.salvarDadosUsuario(binding.salario.text.toString(), binding.nome.text.toString())
        }

        viewModel.userState.observe(this) { userState ->
            if (userState.isSuccess) {
                val bundle = Bundle().apply {
                    putString(Constants.SHARED_NAME_KEY, binding.nome.text.toString())
                    putString(Constants.SHARED_SALARY_KEY, binding.salario.text.toString())
                }
                setResult(Activity.RESULT_OK, Intent().putExtra("bundle", bundle))
                finish()
            }
        }
    }
}