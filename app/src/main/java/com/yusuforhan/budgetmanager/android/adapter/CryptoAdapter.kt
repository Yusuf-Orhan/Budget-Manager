package com.yusuforhan.budgetmanager.android.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yusuforhan.budgetmanager.android.Model.CryptoModel
import com.yusuforhan.budgetmanager.android.databinding.CryptoListRowBinding
import com.yusuforhan.budgetmanager.android.util.downloadFromUri
import java.util.ArrayList

class CryptoAdapter(var crypto_list : ArrayList<CryptoModel>) : RecyclerView.Adapter<CryptoAdapter.ViewHolder>() {
    class ViewHolder(val binding : CryptoListRowBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CryptoListRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return crypto_list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.crypToName.setText(crypto_list[position].currency)
        holder.binding.cryptoPrice.setText(crypto_list[position].price)

    }
    fun uploadData(new_List : List<CryptoModel>){
        crypto_list.clear()
        crypto_list.addAll(new_List)
        notifyDataSetChanged()
    }
}