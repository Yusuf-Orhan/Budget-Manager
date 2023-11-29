package com.yusuforhan.budgetmanager.android.Service

import com.yusuforhan.budgetmanager.android.Model.BudgetModel

interface IncomeListener {
    fun setOnClickListener(budgetModel: BudgetModel)

}