package com.example.sweetcontactget.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.setMargins
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sweetcontactget.Data.Contact
import com.example.sweetcontactget.databinding.IndexHolderBinding
import com.example.sweetcontactget.databinding.PersonInfoHolderBinding

class ContactAdapter(val items : MutableList<Contact> ) : ListAdapter<Contact, RecyclerView.ViewHolder>(object : DiffUtil.ItemCallback<Contact>(){
    override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem == newItem
    }
}){

    inner class IndexHolder(private val binding: IndexHolderBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: Contact.ContactIndex){
            binding.apply {
                tvIndexLetter.text = item.letter
            }
        }

    }
    inner class PersonInfoHolder(private val binding: PersonInfoHolderBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: Contact.SweetieInfo){
            binding.apply {
                ivSweetiePhoto.setImageResource(item.imgSrc)
                tvSweetieName.text = item.name
            }
        }
    }

    companion object{
        const val VIEW_TYPE_HEADER = 1
        const val VIEW_TYPE_LIST = 2
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            VIEW_TYPE_HEADER -> {
                val binding = IndexHolderBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                IndexHolder(binding)
            }
            VIEW_TYPE_LIST -> {
                val binding = PersonInfoHolderBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                PersonInfoHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid View Type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        when(item){
            is Contact.ContactIndex -> {
                val indexHolder = holder as IndexHolder
                indexHolder.bind(item)
            }
            is Contact.SweetieInfo ->{
                val personInfoHolder = holder as PersonInfoHolder
                personInfoHolder.bind(item)

            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(items[position]){
            is Contact.ContactIndex -> VIEW_TYPE_HEADER
            is Contact.SweetieInfo -> VIEW_TYPE_LIST
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}