package com.yusuforhan.budgetmanager.android.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayoutMediator
import com.yusuforhan.budgetmanager.android.R
import com.yusuforhan.budgetmanager.android.ViewModel.HomeViewModel
import com.yusuforhan.budgetmanager.android.adapter.ViewPagerAdapter
import com.yusuforhan.budgetmanager.android.databinding.BottomsheetlayoutBinding
import com.yusuforhan.budgetmanager.android.databinding.CustomAlertdialogBinding
import com.yusuforhan.budgetmanager.android.databinding.FragmentHomeBinding
import java.lang.Exception


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var adapter: ArrayAdapter<String>
    lateinit var viewModel : HomeViewModel
    lateinit var vpadapter : ViewPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModel.getCategorySize()
        try{
            vpadapter = ViewPagerAdapter(childFragmentManager,lifecycle)
            binding.viewPager2.adapter = vpadapter
            val titleList = listOf<String>("Income","Expense")
            TabLayoutMediator(binding.tablayout,binding.viewPager2) { tab, position ->
                tab.text = titleList[position]
            }.attach()

        }catch (e : Exception){
            println("ERROR : ${e.message}")
            e.printStackTrace()
        }

    }

}