package com.yusuforhan.budgetmanager.android.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.yusuforhan.budgetmanager.android.ui.ExpenseFragment
import com.yusuforhan.budgetmanager.android.ui.IncomeFragment

class ViewPagerAdapter(fragmentManager: FragmentManager,lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {
    val fragmentList = listOf<Fragment>(IncomeFragment(),ExpenseFragment())
    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}