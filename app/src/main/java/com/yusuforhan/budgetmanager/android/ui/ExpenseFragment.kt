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
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.yusuforhan.budgetmanager.android.Model.BudgetModel
import com.yusuforhan.budgetmanager.android.R
import com.yusuforhan.budgetmanager.android.Service.ExpenseListener
import com.yusuforhan.budgetmanager.android.ViewModel.ExpenseViewModel
import com.yusuforhan.budgetmanager.android.ViewModel.HomeViewModel
import com.yusuforhan.budgetmanager.android.ViewModel.MainViewModel
import com.yusuforhan.budgetmanager.android.adapter.ExpenseAdapter
import com.yusuforhan.budgetmanager.android.databinding.FragmentExpenseBinding

class ExpenseFragment : Fragment() , ExpenseListener {
    lateinit var viewModel : ExpenseViewModel
    lateinit var adapter : ExpenseAdapter
    lateinit var binding: FragmentExpenseBinding
    private val parentViewModel: MainViewModel by viewModels({ requireActivity() })
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ExpenseViewModel::class.java)
        adapter = ExpenseAdapter(arrayListOf(),this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()),R.layout.fragment_expense,null,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewExpense.adapter = adapter
        obserVeLiveData()
    }

    override fun setOnExpenseItemClickListener(budgetModel: BudgetModel) {
        showDialog(budgetModel)
    }
    fun obserVeLiveData(){
        parentViewModel.expenseList.observe(viewLifecycleOwner){
            adapter.getData(it)
        }
    }
    fun showDialog(budgetModel: BudgetModel){
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