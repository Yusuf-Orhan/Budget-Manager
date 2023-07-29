package com.yusuforhan.budgetmanager.android.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.yusuforhan.budgetmanager.android.Model.BudgetModel

@Database([BudgetModel::class], version = 3)
abstract class BudgetDataBase : RoomDatabase(){
    abstract fun budgetDao() : BudgetDao

    companion object {
        @Volatile private var instance : BudgetDataBase? = null

        private val lock = Any()

        operator fun invoke(context : Context) = instance ?: synchronized(lock) {
            instance ?: makeDatabase(context).also {
                instance = it
            }
        }


        private fun makeDatabase(context : Context) = Room.databaseBuilder(
            context.applicationContext,BudgetDataBase::class.java,"budgetdatabase"
        ).fallbackToDestructiveMigration().build()
    }
}