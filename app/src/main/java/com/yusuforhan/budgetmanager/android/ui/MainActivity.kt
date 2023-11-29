package com.yusuforhan.budgetmanager.android.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListPopupWindow
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.yusuforhan.budgetmanager.android.BudgetManagerApplication
import com.yusuforhan.budgetmanager.android.R
import com.yusuforhan.budgetmanager.android.ViewModel.MainViewModel
import com.yusuforhan.budgetmanager.android.ViewModel.MainViewModelFactory
import com.yusuforhan.budgetmanager.android.databinding.ActivityMainBinding
import com.yusuforhan.budgetmanager.android.databinding.CustomAlertdialogBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var customBinding : CustomAlertdialogBinding
    val categoryType = arrayOf("Income","Expense")
    var categories = arrayOf("Food Shopping","Grocery Shopping","Clothes Shopping","House Rent","Other")
    var categorees2 = arrayOf("Wage","Allowance","Investment","Freelancer","Other")
    lateinit var adapter: ArrayAdapter<String>
    lateinit var adapter2 : ArrayAdapter<String>
    lateinit var adapter3 : ArrayAdapter<String>
    lateinit var alertDialogBuilder: MaterialAlertDialogBuilder
    lateinit var alertDialog : AlertDialog

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(
            (application as BudgetManagerApplication).database.budgetDao()
        )

    }
    var keepSplashOpened = true

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition{
            keepSplashOpened
        }
        CoroutineScope(Dispatchers.Main).launch {
            delay(100)
            keepSplashOpened = false
        }
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val navHostFragment : NavHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        NavigationUI.setupWithNavController(binding.bottomNav, navHostFragment.navController)
        binding.bottomNav.menu.getItem(1).isEnabled = false

        saveData()
        NavigationUI.setupWithNavController(binding.navView, navHostFragment.navController)
        val toogle = ActionBarDrawerToggle(this, binding.drawer, binding.toolbar, 0, 0)
        toogle.drawerArrowDrawable.color= resources.getColor(R.color.white)
        binding.drawer.addDrawerListener(toogle)

        toogle.syncState()
        binding.navView.setNavigationItemSelectedListener{ item->
            when (item.itemId) {
                R.id.share -> {
                    Toast.makeText(applicationContext, "Share App", Toast.LENGTH_SHORT).show()
                    shareApp(this@MainActivity)
                    true
                }
                R.id.rating -> {
                    Toast.makeText(applicationContext, "Rating App", Toast.LENGTH_SHORT).show()
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=$packageName")
                        )
                    )
                    true
                }
                R.id.deleteAll -> {
                    Toast.makeText(applicationContext, "Delete All Budget,", Toast.LENGTH_SHORT).show()
                    val alertDialog = MaterialAlertDialogBuilder(this)
                    alertDialog.background = resources.getDrawable(R.color.white)
                    alertDialog.setTitle("Warning")
                    alertDialog.setMessage("Are you sure you want to delete all data?")
                    alertDialog.setPositiveButton("Yes") { dialog, which -> viewModel.deleteAllBudget() }
                    alertDialog.setNegativeButton("No"
                    ) { dialog, which -> }.create().show()

                    true
                }
                R.id.about_us -> {
                    true
                }
                else ->false
            }
        }
    }
    fun saveData(){
        customBinding = DataBindingUtil.inflate<CustomAlertdialogBinding>(
            LayoutInflater.from(this),
            R.layout.custom_alertdialog,
            null,
            false
        )
        observeLiveData()
        val listPopupWindow = ListPopupWindow(this)
        adapter = ArrayAdapter(applicationContext, R.layout.spinner_text, categoryType)
        adapter2 = ArrayAdapter(applicationContext, R.layout.spinner_text, categories)
        adapter3 = ArrayAdapter(applicationContext, R.layout.spinner_text, categorees2)

        customBinding.autoCompleteTextView2.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                when(position){
                    0 -> {
                        customBinding.categpryEditText.setText("")
                        customBinding.categpryEditText.setAdapter(adapter3)
                    }

                    1 -> {
                        customBinding.categpryEditText.setText("")
                        customBinding.categpryEditText.setAdapter(adapter2)
                    }
                }
            }
        customBinding.mainActivity = this
        alertDialogBuilder = MaterialAlertDialogBuilder(this)
        alertDialog = alertDialogBuilder.create()
        binding.fab.setOnClickListener { view2 ->
            alertDialog.setView(customBinding.root)
            alertDialog.show()

        }
    }
    fun saveClick(amount : String,type : String,category : String,title : String){
        viewModel.saveData(amount,type,category,title)
        if (viewModel.isEmpty.value != true){
            customBinding.titleText.setText("")
            customBinding.autoCompleteTextView2.setText("")
            customBinding.amountText.setText("")
            customBinding.categpryEditText.setText("")
            alertDialog.cancel()
        }
    }
    fun cancelButtonClick() : Unit{
        alertDialog.cancel()
    }
    fun observeLiveData() {
        viewModel.isEmpty.let {
            it.observe(this) {
                if (it) {
                    Toast.makeText(this, "Fill in the blanks", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
    private fun shareApp(context : Context){
        val packageName : String = context.packageName
        val sendIntent = Intent()
        sendIntent.setAction(Intent.ACTION_SEND)
        sendIntent.putExtra(Intent.EXTRA_TEXT,"Download Now : https://play.google.com/store/apps/details?id=$packageName")
        sendIntent.setType("text/plain")
        context.startActivity(sendIntent)
    }

}