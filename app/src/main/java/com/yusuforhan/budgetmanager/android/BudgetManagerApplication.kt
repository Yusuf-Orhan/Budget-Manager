package com.yusuforhan.budgetmanager.android

import android.app.Application
import com.yusuforhan.budgetmanager.android.Database.BudgetDataBase

class BudgetManagerApplication : Application() {
    val database: BudgetDataBase by lazy { BudgetDataBase.invoke(this) }
}