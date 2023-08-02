package com.yusuforhan.budgetmanager.android.ViewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yusuforhan.budgetmanager.android.Database.BudgetDao
import com.yusuforhan.budgetmanager.android.Database.BudgetDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class DashBoardViewModel(private val budgetDao : BudgetDao) : ViewModel() {
    var totalBalance: MutableLiveData<Int> = MutableLiveData(0)
    var totalIncome: MutableLiveData<Int> = MutableLiveData(0)
    var totalExpense: MutableLiveData<Int> = MutableLiveData(0)
    fun getTotalBalance() = viewModelScope.launch {
        var totalI: Int = 0
        var totalE: Int = 0

        budgetDao.getAllIncome().forEach { budgets ->
            totalI += budgets.amount
        }
        budgetDao.getAllExpense().forEach { budgets ->
            totalE += budgets.amount
        }

        totalIncome.postValue(totalI)
        totalExpense.postValue(totalE)
        println("Income : ${totalIncome.value}")
        println("Expense : ${totalExpense.value}")
        totalBalance.value = totalE - totalI

    }

}