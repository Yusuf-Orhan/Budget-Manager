package com.yusuforhan.budgetmanager.android.ui

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.compose.material3.TopAppBar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yusuforhan.budgetmanager.android.Database.BudgetDataBase
import com.yusuforhan.budgetmanager.android.Model.BudgetModel
import com.yusuforhan.budgetmanager.android.R
import com.yusuforhan.budgetmanager.android.Service.IncomeListener
import com.yusuforhan.budgetmanager.android.ViewModel.HomeViewModel
import com.yusuforhan.budgetmanager.android.ViewModel.IncomeViewModel
import com.yusuforhan.budgetmanager.android.ViewModel.MainViewModel
import com.yusuforhan.budgetmanager.android.adapter.IncomeAdapter
import com.yusuforhan.budgetmanager.android.databinding.BottomsheetlayoutBinding
import com.yusuforhan.budgetmanager.android.databinding.CustomAlertdialogBinding
import com.yusuforhan.budgetmanager.android.databinding.FragmentHomeBinding
import com.yusuforhan.budgetmanager.android.databinding.FragmentIncomeBinding

class IncomeFragment : Fragment() ,IncomeListener{
    lateinit var binding : FragmentIncomeBinding
    lateinit var viewModel: IncomeViewModel
    lateinit var adapter : IncomeAdapter
    lateinit var customBinding : CustomAlertdialogBinding
    lateinit var bottomSheetBinding : BottomsheetlayoutBinding
    private val parentViewModel: MainViewModel by viewModels({ requireActivity() })
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()),R.layout.fragment_income,null,false)
        viewModel = ViewModelProvider(this).get(IncomeViewModel::class.java)
        binding.fragmentIncome = this
        bottomSheetBinding = DataBindingUtil.inflate<BottomsheetlayoutBinding>(LayoutInflater.from(requireContext()),R.layout.bottomsheetlayout,null,false)
        bottomSheetBinding.incomeFragment = this
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = IncomeAdapter(this,arrayListOf(),requireContext(),BudgetDataBase(requireContext()).budgetDao())
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        observeLiveData()
    }

    fun observeLiveData() {
        parentViewModel.listIncome.observe(viewLifecycleOwner) {
            if (it.isEmpty()){
                Toast.makeText(requireContext(),"Kaydedilmiş Veri Yok",Toast.LENGTH_SHORT).show()
                binding.blankText.visibility = View.VISIBLE
            }else{
                binding.blankText.visibility = View.INVISIBLE
            }
            adapter.getData(it)
        }

    }
    override fun setOnClickListener(budgetModel: BudgetModel) {
       showDialog(budgetModel)
    }
    fun showDialog(budgetModel: BudgetModel){
        bottomSheetBinding = DataBindingUtil.inflate<BottomsheetlayoutBinding>(LayoutInflater.from(requireContext()),R.layout.bottomsheetlayout,null,false)
        bottomSheetBinding.incomeFragment = this
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.bottomsheetlayout)
        dialog.show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setWindowAnimations(R.style.BottomAnimation)
        dialog.window?.setGravity(Gravity.BOTTOM)
        dialog.findViewById<CardView>(R.id.edit_card_view).setOnClickListener {

        }
        dialog.findViewById<CardView>(R.id.delete_card_view).setOnClickListener {
            parentViewModel.deleteItem(budgetModel)
            dialog.hide()
        }
    }

}