package com.yusuforhan.budgetmanager.android.Database

import androidx.room.*
import com.yusuforhan.budgetmanager.android.Model.BudgetModel

@Dao
interface BudgetDao {
   /* @Query("SELECT * FROM Budgets")
    suspend fun getAllBudget() : List<BudgetModel>?

    */
    @Query("SELECT * FROM Budgets WHERE type = 'Income'")
    suspend fun getAllIncome() : List<BudgetModel>
    @Query("SELECT * FROM Budgets WHERE type = 'Expense'")
    suspend fun getAllExpense() : List<BudgetModel>
    @Insert
    suspend fun insert(budgets : BudgetModel)
    @Query ("DELETE  FROM Budgets")
    suspend fun deleteAll()

    /*@Query("UPDATE Budgets SET totalBalance = :newTotal WHERE uuid = :budgetId")
    suspend fun updateTotalBalance(budgetId: Int, newTotal: Int)

     */
   /*@Query("UPDATE Budgets SET total_balance = :newBalance WHERE uuid = :budgetId")
    suspend fun updateTotalBalance(budgetId: Int, newBalance: Int)*/
    @Delete
    suspend fun delete(budgets: BudgetModel)
}