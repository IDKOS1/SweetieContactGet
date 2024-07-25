package com.example.sweetcontactget.fragments.randomCall

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sweetcontactget.data.Contact
import com.example.sweetcontactget.databinding.IndexHolderBinding
import com.example.sweetcontactget.databinding.ItemRecentRandomCallBinding

class RecentRandomCallListViewAdapter : ListAdapter<Contact, RecyclerView.ViewHolder>(diffUtil) {

    inner class RecentRandomViewHolder(private val binding: ItemRecentRandomCallBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Contact.SweetieInfo) {
            binding.apply {
                ivRecentProfile.setImageResource(item.imgSrc)
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
        if(holder is RecentRandomViewHolder){
            holder.bind(item as Contact.SweetieInfo)
        }
    }

    companion object {

        val diffUtil = object : DiffUtil.ItemCallback<Contact>() {
            override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
                TODO("Not yet implemented")
            }

            override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
                TODO("Not yet implemented")
            }

        }
    }
}