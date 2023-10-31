package com.zamfir.maxcalculadora.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.get
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import com.google.android.material.appbar.MaterialToolbar
import com.zamfir.maxcalculadora.R
import com.zamfir.maxcalculadora.databinding.ActivityMainBinding
import com.zamfir.maxcalculadora.util.Constants
import com.zamfir.maxcalculadora.view.dialog.EditUserBottomSheet
import com.zamfir.maxcalculadora.view.fragment.FragmentFerias
import com.zamfir.maxcalculadora.view.fragment.FragmentMeta
import com.zamfir.maxcalculadora.view.fragment.FragmentTrimestral
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var toolbar : MaterialToolbar? = null

    private var salary : String? = null
    private var name : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSplashScreen()
        setFirstTimeActivityCalling()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbar = binding.toolbar

        binding.navigationDrawer.setCheckedItem(binding.navigationDrawer.menu[0].itemId)

        if(!salary.isNullOrBlank()){
            setHeaderValues(salary, name)
            goToTrimestral(Bundle().apply {
                putString("salario", salary)
            })
        }

        val modalBottomSheet = EditUserBottomSheet()

        binding.navigationDrawer.getHeaderView(0).findViewById<ImageButton>(R.id.edit_salary).setOnClickListener {
            modalBottomSheet.show(supportFragmentManager, EditUserBottomSheet.TAG)
        }

        configNavigationDrawer()

        salaryVisibilityConfig()
    }

    private fun setFirstTimeActivityCalling() {
        val activityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            if (activityResult.resultCode == RESULT_OK) {
                activityResult.data?.getBundleExtra("bundle")?.let { bundle ->

                    setHeaderValues(bundle.getString(Constants.SHARED_SALARY_KEY), bundle.getString(Constants.SHARED_NAME_KEY))

                    goToTrimestral(Bundle().apply {
                        putString("salario", bundle.getString(Constants.SHARED_SALARY_KEY))
                    })
                }
            }
        }

        if (getSalary().isBlank()) {
            activityResult.launch(Intent(this, FirstAccessActivity::class.java))
        }
    }

    private fun setSplashScreen() {
        installSplashScreen().apply {
            lifecycleScope.launch {
                salary = getSalary()
                name = getNome()
            }
        }
    }

    fun setHeaderValues(salario : String?, nome : String?) {
        binding.navigationDrawer.getHeaderView(0).also {
            it.findViewById<TextView>(R.id.salario_usuario).text = salario ?: ""
            it.findViewById<TextView>(R.id.nome_usuario).text = nome ?: ""
        }
    }

    fun configNavigationDrawer() {

        binding.toolbar.navigationIcon = AppCompatResources.getDrawable(this, R.drawable.baseline_menu_24)

        val bundleSalario = Bundle().apply {
            putString("salario", binding.navigationDrawer.getHeaderView(0).findViewById<TextView>(R.id.salario_usuario).text?.toString())
        }

        binding.toolbar.setNavigationOnClickListener {
            binding.drawerLayout.open()
        }

        binding.navigationDrawer.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            when (menuItem.itemId) {
                binding.navigationDrawer.menu.findItem(R.id.trimestral).itemId -> {
                    goToTrimestral(bundleSalario)
                    binding.drawerLayout.close()
                }

                binding.navigationDrawer.menu.findItem(R.id.meta).itemId -> {
                    goToMeta(bundleSalario)
                    binding.drawerLayout.close()
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
    }

    private fun goToMeta(bundleSalario: Bundle) {
        val fragment = FragmentMeta()
        fragment.arguments = bundleSalario
        supportFragmentManager.commit {
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            replace(R.id.nav_host_fragment, fragment)
            addToBackStack(null)
            binding.drawerLayout.close()
        }
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