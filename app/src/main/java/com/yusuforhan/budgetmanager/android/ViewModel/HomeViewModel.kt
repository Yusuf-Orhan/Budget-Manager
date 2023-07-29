package com.yusuforhan.budgetmanager.android.ViewModel

import android.app.Application
import android.icu.text.CaseMap.Title
import android.text.Editable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.yusuforhan.budgetmanager.android.Database.BudgetDao
import com.yusuforhan.budgetmanager.android.Database.BudgetDataBase
import com.yusuforhan.budgetmanager.android.Model.BudgetModel
import kotlinx.coroutines.launch
import org.w3c.dom.Text

class HomeViewModel(application: Application) : BaseViewModel(application) {
    var totalBalance : MutableLiveData<Int?> = MutableLiveData<Int?>(0)


    var foodShopping : MutableLiveData<Int> = MutableLiveData(0)
    var groceryShooping : MutableLiveData<Int> = MutableLiveData(0)
    var clothesShooping : MutableLiveData<Int> = MutableLiveData(0)
    var hoseRent : MutableLiveData<Int> = MutableLiveData(0)
    var other : MutableLiveData<Int> = MutableLiveData(0)
    val categoryMap = HashMap<String,Int>()


    fun getCategorySize() : HashMap<String,Int>{
        launch {
            BudgetDataBase(getApplication()).budgetDao().getAllExpense().forEach { budgets ->
                when (budgets.category) {
                    "Food Shopping" -> {
                        val i: Int = foodShopping.value?.plus(1)!!
                        categoryMap.put("Food Shopping", i)
                    }

                    "Grocery Shopping" -> {
                        groceryShooping.value = groceryShooping.value?.plus(1)
                        categoryMap.put("Grocery Shopping", groceryShooping.value!!)

                    }

                    "Clothes Shopping" -> {
                        clothesShooping.value = clothesShooping.value?.plus(1)
                        categoryMap.put("Clothes Shopping", clothesShooping.value!!)
                    }

                    "House Rent" -> {
                        hoseRent.value = hoseRent.value?.plus(1)
                        categoryMap.put("House Rent", hoseRent.value!!)
                    }

                    "Other" -> {
                        other.value = other.value?.plus(1)
                        categoryMap.put("Other", other.value!!)
                    }
                }
            }
        }
        return categoryMap
    }

}