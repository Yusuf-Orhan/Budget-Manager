package com.yusuforhan.budgetmanager.android.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Budgets")
data class BudgetModel(val amount : Int, val type : String, val category: String,val title : String)
{
    @PrimaryKey(autoGenerate = true)
    var uuid : Int = 0
}