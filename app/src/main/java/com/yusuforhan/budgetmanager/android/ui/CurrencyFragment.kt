package com.yusuforhan.budgetmanager.android.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.yusuforhan.budgetmanager.android.R
import com.yusuforhan.budgetmanager.android.ViewModel.CurrencyViewModel
import com.yusuforhan.budgetmanager.android.adapter.CryptoAdapter
import com.yusuforhan.budgetmanager.android.databinding.FragmentCurrencyBinding

class CurrencyFragment : Fragment() {
    val viewModel by lazy {CurrencyViewModel(application = requireActivity().application)}
    lateinit var binding : FragmentCurrencyBinding
    var adapter  = CryptoAdapter(arrayListOf())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_currency, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cryptoRecyclerView.adapter = adapter
        binding.cryptoRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        viewModel.getCurrency()
        observeLiveData()
        binding.swipeRefreshLayout.setOnRefreshListener {
            println("Refresh Edildi")
            binding.swipeRefreshLayout.isRefreshing = false
            viewModel.getCurrency()
        }
    }
    private fun search(){
        binding.serachView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                println(newText)
                return false
            }

        })
    }
    private fun observeLiveData(){
        viewModel.cryptoError.observe(viewLifecycleOwner){error ->
            when(error){
                true -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    binding.cryptoRecyclerView.visibility = View.INVISIBLE
                    binding.errorImageview.visibility = View.VISIBLE
                }
                false -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    binding.cryptoRecyclerView.visibility = View.VISIBLE
                    binding.errorImageview.visibility = View.INVISIBLE
                }
                else -> {}
            }

        }
        viewModel.cryptoLoading.observe(viewLifecycleOwner){loading ->
            if(loading == true){
                binding.progressBar.visibility = View.VISIBLE
                binding.cryptoRecyclerView.visibility = View.INVISIBLE
                binding.errorImageview.visibility = View.INVISIBLE
            }else{
                binding.progressBar.visibility = View.INVISIBLE
                binding.cryptoRecyclerView.visibility = View.VISIBLE
                binding.errorImageview.visibility = View.INVISIBLE
            }
        }
        viewModel.crypto.observe(viewLifecycleOwner){
            adapter.uploadData(it)
        }
    }

}