package com.sachin.app.qrscanner.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sachin.app.qrscanner.databinding.ResultListItemBinding
import com.sachin.app.qrscanner.db.ScanResult


class HistoryAdapter() : ListAdapter<ScanResult, HistoryAdapter.ResultViewHolder>(
    object : DiffUtil.ItemCallback<ScanResult>() {
        override fun areItemsTheSame(oldItem: ScanResult, newItem: ScanResult): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ScanResult, newItem: ScanResult): Boolean {
            return oldItem == newItem
        }
    }
) {

    class ResultViewHolder(private val binding: ResultListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(result: ScanResult) {
            binding.resultTextview.text = result.text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        return ResultViewHolder(
            ResultListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}