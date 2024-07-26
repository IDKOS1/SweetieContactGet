package com.example.sweetcontactget.fragments.randomCall

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sweetcontactget.data.SweetieInfo
import com.example.sweetcontactget.databinding.ItemRecentRandomCallBinding

class RecentRandomCallListViewAdapter : ListAdapter<SweetieInfo, RecyclerView.ViewHolder>(diffUtil) {

    inner class RecentRandomViewHolder(private val binding: ItemRecentRandomCallBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SweetieInfo) {
            binding.apply {
                ivRecentProfile.setImageDrawable(item.imgSrc)
                tvRecentName.text = item.name
                tvRecentNumber.text = item.number
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RecentRandomViewHolder(
            ItemRecentRandomCallBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = currentList[position]
        if (holder is RecentRandomViewHolder) {
            holder.bind(item)
        }
    }

    companion object {

        val diffUtil = object : DiffUtil.ItemCallback<SweetieInfo>() {
            override fun areItemsTheSame(oldItem: SweetieInfo, newItem: SweetieInfo): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: SweetieInfo, newItem: SweetieInfo): Boolean {
                return oldItem == newItem
            }

        }
    }
}