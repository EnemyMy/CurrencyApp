package com.example.app_41_currencyapp.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.app_41_currencyapp.data.ChosenCurrency
import com.example.app_41_currencyapp.databinding.FragmentMainRecyclerItemBinding
import java.util.*

class MainFragmentAdapter: ListAdapter<ChosenCurrency, MainFragmentAdapter.RecyclerHolder>(IdeasDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerHolder {
        val binding = FragmentMainRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecyclerHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerHolder, position: Int) {
        val binding = holder.binding
        binding.mainRecyclerItemTitle.text = currentList[position].title
        binding.mainRecyclerItemValue.text = String.format(Locale.US, "%.2f", currentList[position].value)
    }

    inner class RecyclerHolder(val binding: FragmentMainRecyclerItemBinding): RecyclerView.ViewHolder(binding.root)

}

class IdeasDiffCallback: DiffUtil.ItemCallback<ChosenCurrency>() {
    override fun areItemsTheSame(oldItem: ChosenCurrency, newItem: ChosenCurrency): Boolean = oldItem.title == newItem.title

    override fun areContentsTheSame(oldItem: ChosenCurrency, newItem: ChosenCurrency): Boolean = oldItem == newItem
}