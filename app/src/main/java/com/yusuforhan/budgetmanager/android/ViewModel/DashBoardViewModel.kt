package com.yusuforhan.budgetmanager.android.ViewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.yusuforhan.budgetmanager.android.Database.BudgetDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DashBoardViewModel(application: Application,) : BaseViewModel(application) {
    var totalBalance : MutableLiveData<Int> = MutableLiveData(0)
    var totalIncome1: MutableLiveData<Int> = MutableLiveData(0)
    var totalExpense2 : MutableLiveData<Int> = MutableLiveData(0)
    // var categorees2 = arrayOf("Wage","Allowance","Investment","Freelancer","Other")
    /*var wage = MutableLiveData<Int>(1)
    var allowance = MutableLiveData<Int>(1)
    var investment = MutableLiveData<Int>(1)
    var freelancer = MutableLiveData<Int>(1)
    var otherIncome = MutableLiveData<Int>(1)
    var foodShopping : MutableLiveData<Int> = MutableLiveData(1)
    var groceryShooping : MutableLiveData<Int> = MutableLiveData(1)
    var clothesShooping : MutableLiveData<Int> = MutableLiveData(1)
    var hoseRent : MutableLiveData<Int> = MutableLiveData(1)
    var otherExpense : MutableLiveData<Int> = MutableLiveData()
    var expenseCategoryMap = HashMap<String,Int>()
    var incomeCategoryMap = HashMap<String,Int>()
    val hashMap : MutableLiveData<HashMap<String,Int>> = MutableLiveData()
    var control = MutableLiveData<Boolean>(false)

     */
    fun getTotalBalance() {
        var totalIncome : Int = 0
        var totalExpense : Int = 0
        launch{
            BudgetDataBase(getApplication()).budgetDao().getAllIncome().forEach { budgets ->
               totalIncome += budgets.amount
            }
            BudgetDataBase(getApplication()).budgetDao().getAllExpense().forEach { budgets ->
                totalExpense += budgets.amount
            }
            totalIncome1.value = totalIncome
            totalExpense2.value = totalExpense
            println("Income : ${totalIncome1.value}")
            println("Expense : ${totalExpense2.value}")
            totalBalance.value = totalIncome - totalExpense
        }


    }
  /*  fun getCategorySize(){
        launch {
            println("Launch Bloğu Başladı")
            BudgetDataBase(getApplication()).budgetDao().getAllExpense().forEach { budgets ->
                when (budgets.category) {
                    "Food Shopping" -> {
                        val i: Int = foodShopping.value?.plus(1)!!
                        addExpenseHashMap("Food Shopping",i)
                    }

                    "Grocery Shopping" -> {
                        groceryShooping.value = groceryShooping.value?.plus(1)
                        addExpenseHashMap("Grocery Shopping",groceryShooping.value)
                    }

                    "Clothes Shopping" -> {
                        clothesShooping.value = clothesShooping.value?.plus(1)
                        addExpenseHashMap("Clothes Shopping",clothesShooping.value)

                    }

                    "House Rent" -> {
                        hoseRent.value = hoseRent.value?.plus(1)
                        addExpenseHashMap("House Rent",hoseRent.value)
                    }

                    "Other" -> {
                        otherExpense.value = otherExpense.value?.plus(1)
                        addExpenseHashMap("Other",otherExpense.value)
                    }
                }
            }
            BudgetDataBase(getApplication()).budgetDao().getAllIncome().forEach{budgets ->
                when (budgets.category) {
                    "Wage" -> {
                        wage.value = wage.value?.plus(1)
                        addIncomeHashMap("Wage",wage.value)
                    }

                    "Allowance" -> {
                        allowance.value = allowance.value?.plus(1)
                        addIncomeHashMap("Allowance",allowance.value)
                    }

                    "Investment" -> {
                        investment.value = investment.value?.plus(1)
                        addIncomeHashMap("Investment",investment.value)

                    }

                    "Freelancer" -> {
                        freelancer.value = freelancer.value?.plus(1)
                        addIncomeHashMap("Freelancer",freelancer.value)
                    }

                    "Other" -> {
                        otherIncome.value = otherIncome.value?.plus(1)
                        addIncomeHashMap("Other",otherIncome.value)
                    }
                }
            }

            println("Launch Bloğu Bitti")
            control.value = true
        }
    }
    fun addExpenseHashMap(name : String, value : Int?){
        if (value != null){
            expenseCategoryMap.put(name,value)
        }else{
            expenseCategoryMap.put(name,1)
        }
    }
    fun addIncomeHashMap(name : String,value: Int?){
        if (value != null){
            incomeCategoryMap.put(name,value)
        }else{
            incomeCategoryMap.put(name,1)
        }
    }
    fun test() : HashMap<String,Int>{
        val map = hashMapOf<String,Int>()
        var totalWage = Any()
        var totalAlloWance = Any()
        var totalInvestment = Any()
        var totalFreelancer = Any()
        var totalOther = Any()
        incomeCategoryMap.forEach {name ,value ->
            if (name == "Wage"){
                totalWage =  value.toInt()
            }else if (name == "Allowance"){
                totalAlloWance = value.toInt()
            }else if (name == "Investment"){
                totalInvestment = value.toInt()
            }else if (name == "Freelancer") {
                totalFreelancer = value.toInt()
            }else if (name == "Other"){
                totalOther = value.toInt()
            }
        }
        map.put("Wage",totalWage.toString().toInt())
        map.put("Allowance",totalAlloWance.toString().toInt())
        map.put("Investment",totalInvestment.toString().toInt())
        map.put("Freelancer",totalFreelancer.toString().toInt())
        map.put("Other",totalOther.toString().toInt())
        return map
    }

   */
}