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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getTotalBalance()
        observeLiveData()
        pieChart()
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
        /*viewModel.control.observe(viewLifecycleOwner) {
            if (it) {
                barChart(viewModel.expenseCategoryMap, viewModel.test())
            }


        }

         */

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

    private fun barChart(expenseMap: HashMap<String, Int> = hashMapOf(), incomeMap: HashMap<String, Int>) {
        val entries = arrayListOf<BarEntry>()
        val entriesIncome = arrayListOf<BarEntry>()
        if (!expenseMap.isEmpty()) {
            if (expenseMap.get("Other") != null) {
                entries.add(BarEntry(1f, expenseMap.get("Other")?.toFloat()!!))
            } else {
                entries.add(BarEntry(1f, 0.1f))
            }
            if (expenseMap.get("House Rent") != null) {
                entries.add(BarEntry(2f, expenseMap.get("House Rent")?.toFloat()!!))

            } else {
                entries.add(BarEntry(2f, 0.1f))
            }
            if (expenseMap.get("Clothes Shopping") != null) {
                entries.add(BarEntry(3f, expenseMap.get("Clothes Shopping")?.toFloat()!!))

            } else {
                entries.add(BarEntry(3f, 0.1f))
            }
            if (expenseMap.get("Grocery Shopping") != null) {
                entries.add(BarEntry(4f, expenseMap.get("Grocery Shopping")?.toFloat()!!))

            } else {
                entries.add(BarEntry(4f, 0.1f))
            }
            if (expenseMap.get("Food Shopping") != null) {
                entries.add(BarEntry(5f, expenseMap.get("Food Shopping")?.toFloat()!!))

            } else {
                entries.add(BarEntry(5f, 0.1f))
            }
        } else {
            entries.add(BarEntry(1f, 0f))
            entries.add(BarEntry(2f, 0f))
            entries.add(BarEntry(3f, 0f))
            entries.add(BarEntry(4f, 0f))
            entries.add(BarEntry(5f, 0f))
        }
        if (!incomeMap.isEmpty()) {
            if (incomeMap.get("Wage") != null) {
                entriesIncome.add(BarEntry(5f, incomeMap.get("Wage")?.toFloat()!!))
            } else {
                entriesIncome.add(BarEntry(5f, 0.1f))
            }
            if (incomeMap.get("Investment") != null) {
                entriesIncome.add(BarEntry(4f, incomeMap.get("Investment")?.toFloat()!!))
            } else {
                entriesIncome.add(BarEntry( 4f, 0.1f))
            }
            if (incomeMap.get("Allowance") != null) {
                entriesIncome.add(BarEntry(3f, incomeMap.get("Allowance")?.toFloat()!!))
            } else {
                entriesIncome.add(BarEntry(3f, 0.1f))
            }
            if (incomeMap.get("Freelancer") != null) {
                entriesIncome.add(BarEntry(2f, incomeMap.get("Freelancer")?.toFloat()!!))
            } else {
                entriesIncome.add(BarEntry(2f, 0.1f))
            }
            if (incomeMap.get("Other") != null) {
                entriesIncome.add(BarEntry(1f, incomeMap.get("Other")?.toFloat()!!))
            } else {
                entriesIncome.add(BarEntry(1f, 0.1f))

            }
            val incomeBarDataSet = BarDataSet(entriesIncome, "")
            // Çubuk grafiği veri setini oluşturma ve özelleştirme
            val expenseBarDataSet = BarDataSet(entries, "")
            val colors = listOf<Int>(Color.BLACK, Color.BLUE, Color.RED, Color.YELLOW, Color.GREEN)
            val colorsIncome = listOf<Int>(Color.GREEN,Color.YELLOW,Color.RED,Color.BLUE,Color.BLACK)

            expenseBarDataSet.colors = colors
            incomeBarDataSet.colors = colorsIncome
            expenseBarDataSet.setDrawValues(true)
            incomeBarDataSet.setDrawValues(true)

            // Çubuk grafiği veri nesnesini oluşturma
            val expenseBarData: BarData = BarData(expenseBarDataSet)
            val incomeBarData: BarData = BarData(incomeBarDataSet)


            // BarChart görünümüne veri atama ve güncelleme
            /*binding.barChart.description.isEnabled = false
            binding.barChart.data = expenseBarData
            binding.barChart.animateY(1400, Easing.EaseInOutQuad)
            binding.barChart.invalidate()

            binding.incomeBarCard.description.isEnabled = false
            binding.incomeBarCard.data = incomeBarData
            binding.incomeBarCard.animateY(1400, Easing.EaseInOutQuad)
            binding.incomeBarCard.invalidate()

             */
        }
    }
}