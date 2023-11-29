package com.yusuforhan.budgetmanager.android.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.yusuforhan.budgetmanager.android.Model.BudgetModel
import com.yusuforhan.budgetmanager.android.R
import com.yusuforhan.budgetmanager.android.Service.ExpenseListener
import com.yusuforhan.budgetmanager.android.databinding.ExpenseRecyclerRowBinding

class ExpenseAdapter(val expenseList: ArrayList<BudgetModel>, val listener: ExpenseListener) :
    RecyclerView.Adapter<ExpenseAdapter.ExpenseVHolder>() {
    class ExpenseVHolder(val binding: ExpenseRecyclerRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(budgets: BudgetModel, clickListener: ExpenseListener) {
            binding.textViewTitle.text = budgets.title
            binding.textViewDecsription.text = budgets.category
            binding.textViewPrice.text = "-$" + budgets.amount.toString()
            binding.cardView.setOnClickListener {
                clickListener.setOnExpenseItemClickListener(budgets)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseVHolder {
        val binding = DataBindingUtil.inflate<ExpenseRecyclerRowBinding>(
            LayoutInflater.from(parent.context),
            R.layout.expense_recycler_row,
            parent,
            false
        )
        return ExpenseVHolder(binding)
    }

    override fun getItemCount(): Int = expenseList.size

    override fun onBindViewHolder(holder: ExpenseVHolder, position: Int) {
        holder.bind(expenseList.get(position), listener)
    }

    fun getData(newList: List<BudgetModel>) {
        expenseList.clear()
        expenseList.addAll(newList)
        notifyDataSetChanged()
    }
}