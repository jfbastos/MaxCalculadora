package com.zamfir.maxcalculadora.view.activity

import android.content.Context
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import com.google.android.material.appbar.MaterialToolbar
import com.zamfir.maxcalculadora.R
import com.zamfir.maxcalculadora.databinding.ActivityMainBinding
import com.zamfir.maxcalculadora.util.Constants
import com.zamfir.maxcalculadora.util.show
import com.zamfir.maxcalculadora.view.fragment.FirstTimeFragment
import com.zamfir.maxcalculadora.view.fragment.FragmentFerias
import com.zamfir.maxcalculadora.view.fragment.FragmentMeta
import com.zamfir.maxcalculadora.view.fragment.FragmentTrimestral

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var toolbar : MaterialToolbar? = null
    private var salaryIsShowing = false
    private var salarioTextView : TextView? = null
    private var nomeTextView : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbar = binding.toolbar

         binding.navigationDrawer.getHeaderView(0).also{
             salarioTextView = it.findViewById(R.id.salario_usuario)
             nomeTextView = it.findViewById(R.id.nome_usuario)
        }
        salarioTextView?.text = getSalary()
        nomeTextView?.text = getNome()

        val bundleSalario = Bundle().apply {
            putString("salario", salarioTextView?.text.toString())
        }

        if(salarioTextView?.text.isNullOrBlank()){
            binding.toolbar.navigationIcon = null
            supportFragmentManager.commit {
                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                replace(R.id.nav_host_fragment, FirstTimeFragment())
                addToBackStack(null)
            }
        }else{
            binding.navigationDrawer.setCheckedItem(binding.navigationDrawer.menu[0].itemId)
            goToTrimestral(bundleSalario)
        }

        binding.toolbar.setNavigationOnClickListener {
            binding.drawerLayout.open()
        }

        binding.navigationDrawer.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            when(menuItem.itemId){
                binding.navigationDrawer.menu.findItem(R.id.trimestral).itemId -> {
                    goToTrimestral(bundleSalario)
                    binding.drawerLayout.close()
                }
                binding.navigationDrawer.menu.findItem(R.id.meta).itemId -> {
                    supportFragmentManager.commit {
                        setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        replace(R.id.nav_host_fragment, FragmentMeta())
                        addToBackStack(null)
                        binding.drawerLayout.close()
                    }
                }
                binding.navigationDrawer.menu.findItem(R.id.ferias).itemId -> {
                    supportFragmentManager.commit {
                        setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        replace(R.id.nav_host_fragment, FragmentFerias())
                        addToBackStack(null)
                        binding.drawerLayout.close()
                    }
                }
            }
            true
        }

        salaryVisibilityConfig()
    }

    private fun goToTrimestral(bundleSalario: Bundle) {
        val fragment = FragmentTrimestral()
        fragment.arguments = bundleSalario
        supportFragmentManager.commit {
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            replace(R.id.nav_host_fragment, fragment)
            addToBackStack(null)
        }
    }

    private fun salaryVisibilityConfig() {
        val salaryHidder = binding.navigationDrawer.getHeaderView(0).findViewById<CardView>(R.id.salary_hide)
        salaryHidder.show(!salaryIsShowing)

        binding.navigationDrawer.getHeaderView(0).findViewById<ImageButton>(R.id.blur_salary_btn)?.setOnClickListener {
            salaryHidder.show(!salaryHidder.isVisible)
        }
    }

    //TODO -> Passar para uma viewModel
    private fun getSalary() : String{
        val sharedPreferences = this.getSharedPreferences(Constants.SHARED_FILE, Context.MODE_PRIVATE)
        return sharedPreferences.getString(Constants.SHARED_SALARY_KEY, "") ?: ""
    }

    private fun getNome() : String{
        val sharedPreferences = this.getSharedPreferences(Constants.SHARED_FILE, Context.MODE_PRIVATE)
        return sharedPreferences.getString(Constants.SHARED_NAME_KEY, "") ?: ""
    }


}