package com.yusuforhan.budgetmanager.android.ui

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.patrykandpatrick.vico.core.entry.entryModelOf
import com.yusuforhan.budgetmanager.android.Database.BudgetDataBase
import com.yusuforhan.budgetmanager.android.R
import com.yusuforhan.budgetmanager.android.ViewModel.DashBoardViewModel
import com.yusuforhan.budgetmanager.android.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    lateinit var binding: FragmentDashboardBinding
    var income = 0f
    var expense = 0f
    lateinit var pieData: PieData
    lateinit var pieDataSet: PieDataSet
    private val viewModel by lazy { DashBoardViewModel(BudgetDataBase(requireContext()).budgetDao()) }
    val pieList = arrayListOf<PieEntry>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getTotalBalance()
        observeLiveData()
        pieChart()
        val chartEntryModel  = entryModelOf(4f,12f,8f,16f)
    }

    private fun observeLiveData() {
        viewModel.totalBalance.observe(viewLifecycleOwner) {
            binding.totalBalance = "$$it"
        }
        viewModel.totalIncome.observe(viewLifecycleOwner) {
            pieList.clear()
            pieList.add(PieEntry(it.toFloat(), "Income"))
            binding.pieChart.data = pieData

        }
        viewModel.totalExpense.observe(viewLifecycleOwner) {
            pieList.add(PieEntry(it.toFloat(), "Expense"))
            binding.pieChart.data = pieData
        }

    }

    private fun pieChart(incom1e: Float = 100f, expense1: Float = 1000f) {
        pieList.add(PieEntry(income, "Income"))
        pieList.add(PieEntry(expense, "Expense"))
        val colorList = mutableListOf<Int>(Color.GREEN, Color.RED)
        pieDataSet = PieDataSet(pieList, "")
        pieData = PieData(pieDataSet)
        pieDataSet.colors = colorList
        pieDataSet.sliceSpace = 0f
        binding.pieChart.setUsePercentValues(true)
        binding.pieChart.isDrawHoleEnabled = true
        binding.pieChart.description.isEnabled = false
        binding.pieChart.setEntryLabelColor(R.color.white)
        binding.pieChart.setCenterTextColor(R.color.white)
        binding.pieChart.setCenterTextSize(20f)
        binding.pieChart.setUsePercentValues(true)
        binding.pieChart.animateY(1400, Easing.EaseInOutQuad)
        binding.pieChart.data = pieData
        binding.pieChart.setNoDataText("Add a budget first")
        binding.pieChart.setNoDataTextColor(R.color.black)
        binding.pieChart.invalidate()

    }

}