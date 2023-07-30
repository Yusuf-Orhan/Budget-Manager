package com.yusuforhan.budgetmanager.android.ui

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.patrykandpatrick.vico.core.entry.ChartEntryModel
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.FloatEntry
import com.patrykandpatrick.vico.core.entry.entryModelOf
import com.patrykandpatrick.vico.core.extension.setFieldValue
import com.yusuforhan.budgetmanager.android.R
import com.yusuforhan.budgetmanager.android.ViewModel.DashBoardViewModel
import com.yusuforhan.budgetmanager.android.ViewModel.HomeViewModel
import com.yusuforhan.budgetmanager.android.databinding.FragmentDashboardBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import java.lang.NullPointerException
import kotlin.math.exp
import kotlin.properties.Delegates

class DashboardFragment : Fragment() {

    lateinit var binding: FragmentDashboardBinding
    var income = 0f
    var expense = 0f
    lateinit var pieData: PieData
    lateinit var pieDataSet: PieDataSet
    private val viewModel by lazy { DashBoardViewModel(application = requireActivity().application) }
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
        val chart_entry_model  = entryModelOf(4f,12f,8f,16f)
        binding.chartView.setModel(chart_entry_model)
    }

    private fun observeLiveData() {
        viewModel.totalBalance.observe(viewLifecycleOwner) {
            binding.totalBalance = "$$it"
        }
        viewModel.totalIncome1.observe(viewLifecycleOwner) {
            pieList.clear()
            pieList.add(PieEntry(it.toFloat(), "Income"))
            binding.pieChart.data = pieData

        }
        viewModel.totalExpense2.observe(viewLifecycleOwner) {
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
        binding.pieChart.setNoDataTextColor(R.color.purple_500)
        binding.pieChart.invalidate()

    }

}