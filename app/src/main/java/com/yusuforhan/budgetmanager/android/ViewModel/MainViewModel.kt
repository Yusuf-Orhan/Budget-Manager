package com.yusuforhan.budgetmanager.android.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yusuforhan.budgetmanager.android.Database.BudgetDao
import com.yusuforhan.budgetmanager.android.Model.BudgetModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val budgetDao: BudgetDao) : ViewModel() {

    val isEmpty = MutableLiveData<Boolean>()
    var totalBalance: MutableLiveData<Int?> = MutableLiveData<Int?>(0)
    var listIncome = MutableLiveData<List<BudgetModel>>()
    var expenseList = MutableLiveData<List<BudgetModel>>()

    init {
        getAllExpense()
        getAllInCome()
    }

    fun getAllExpense() = viewModelScope.launch(Dispatchers.IO) {
        expenseList.postValue(budgetDao.getAllExpense())
    }

    fun getAllInCome() = viewModelScope.launch(Dispatchers.IO) {
        listIncome.postValue(budgetDao.getAllIncome())
    }

    fun saveData(amount: String, type: String, desripsion: String, title: String) =
        viewModelScope.launch {
            isEmpty.value =
                amount.isEmpty() || type.isEmpty() || desripsion.isEmpty() || title.isEmpty()
            if (isEmpty.value == false) {
                val amount12 = amount.toInt()
                val budgetsModel = BudgetModel(amount12, type, desripsion, title)

                withContext(Dispatchers.IO) {
                    budgetDao.insert(budgetsModel)
                    getAllInCome()
                    getAllExpense()
                }

            }
        }

    fun deleteItem(budgetModel: BudgetModel) = viewModelScope.launch(Dispatchers.IO) {
        budgetDao.delete(budgetModel)
        getAllExpense()
        getAllInCome()

    }

    fun deleteAllBudget() = viewModelScope.launch(Dispatchers.IO) {
        budgetDao.deleteAll()
        getAllExpense()
        getAllInCome()

    }
}

class MainViewModelFactory(private val budgetDao: BudgetDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(budgetDao = budgetDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}