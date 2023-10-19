package com.zamfir.maxcalculadora.view.activity

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import androidx.navigation.NavHostController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.appbar.MaterialToolbar
import com.zamfir.maxcalculadora.R
import com.zamfir.maxcalculadora.databinding.ActivityMainBinding
import com.zamfir.maxcalculadora.util.show
import com.zamfir.maxcalculadora.view.fragment.FragmentFerias
import com.zamfir.maxcalculadora.view.fragment.FragmentMeta
import com.zamfir.maxcalculadora.view.fragment.FragmentSalario
import com.zamfir.maxcalculadora.view.fragment.FragmentTrimestral

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var toolbar : MaterialToolbar? = null
    private var salaryIsShowing = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbar = binding.toolbar




        binding.toolbar.setNavigationOnClickListener {
            binding.drawerLayout.open()
        }

        binding.navigationDrawer.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            when(menuItem.itemId){
                binding.navigationDrawer.menu.findItem(R.id.trimestral).itemId -> {
                    val salario = Bundle().apply {
                        putString("salario", binding.navigationDrawer.getHeaderView(0).findViewById<TextView>(R.id.salary).text.toString())
                    }
                    val fragment = FragmentTrimestral()
                    fragment.arguments = salario
                    supportFragmentManager.commit {
                        setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        replace(R.id.nav_host_fragment, fragment)

                        addToBackStack(null)
                    }
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
                binding.navigationDrawer.menu.findItem(R.id.liquido).itemId -> {
                    supportFragmentManager.commit {
                        setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        replace(R.id.nav_host_fragment, FragmentSalario())
                        addToBackStack(null)
                        binding.drawerLayout.close()
                    }
                }
            }
            true
        }

        salaryVisibilityConfig()
    }

    private fun salaryVisibilityConfig() {
        val salary = binding.navigationDrawer.getHeaderView(0).findViewById<TextView>(R.id.salary)
        val salaryHidder = binding.navigationDrawer.getHeaderView(0).findViewById<CardView>(R.id.salary_hide)

        salary.text = "R$ 3.000,00"
        salaryHidder.show(!salaryIsShowing)

        binding.navigationDrawer.getHeaderView(0).findViewById<ImageButton>(R.id.blur_salary_btn)?.setOnClickListener {
            salaryHidder.show(!salaryHidder.isVisible)
        }
    }


}