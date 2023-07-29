package com.yusuforhan.budgetmanager.android.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yusuforhan.budgetmanager.android.Database.BudgetDao
import com.yusuforhan.budgetmanager.android.Model.BudgetModel
import com.yusuforhan.budgetmanager.android.R
import com.yusuforhan.budgetmanager.android.Service.IncomeListener
import com.yusuforhan.budgetmanager.android.databinding.IncomeRecyclerRowBinding
import java.util.ArrayList

class IncomeAdapter(val ClickListener : IncomeListener, var incomeList : ArrayList<BudgetModel>, val context : Context, var budgetDao: BudgetDao) : RecyclerView.Adapter<IncomeAdapter.IncomeViewHolder>() {
    class IncomeViewHolder(val binding: IncomeRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(budget : BudgetModel,clickListener: IncomeListener){
            binding.textViewTitle.setText(budget.title)
            binding.textViewPrice.setText("+$${budget.amount.toString()}")
            binding.textViewDecsription.setText(budget.category)
            binding.cardView.setOnClickListener {
                clickListener.setOnClickListener(budgetModel = budget)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IncomeViewHolder {
        val binding = IncomeRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        val view = LayoutInflater.from(parent.context).inflate(R.layout.income_recycler_row,parent,false)

        return IncomeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return incomeList.size
    }

    override fun onBindViewHolder(holder: IncomeViewHolder, position: Int) {
        holder.bind(incomeList[position],ClickListener)
    }
    fun getData(list : List<BudgetModel>){
        incomeList.clear()
        incomeList.addAll(list)
        notifyDataSetChanged()
    }

}