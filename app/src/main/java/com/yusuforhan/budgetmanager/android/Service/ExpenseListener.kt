package com.yusuforhan.budgetmanager.android.Service

import com.yusuforhan.budgetmanager.android.Model.BudgetModel

interface ExpenseListener {
    fun setOnExpenseItemClickListener(budgetModel: BudgetModel)
}