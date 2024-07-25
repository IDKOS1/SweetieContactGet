package com.example.sweetcontactget.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sweetcontactget.DetailActivity
import com.example.sweetcontactget.data.Contact
import com.example.sweetcontactget.data.DataObject.contactData
import com.example.sweetcontactget.util.KoreanMatcher
import com.example.sweetcontactget.databinding.IndexHolderBinding
import com.example.sweetcontactget.databinding.PersonInfoHolderBinding

class ContactAdapter :
    ListAdapter<Contact, RecyclerView.ViewHolder>(object : DiffUtil.ItemCallback<Contact>() {
        override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem == newItem
        }
    }), Filterable {

    inner class IndexHolder(private val binding: IndexHolderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Contact.ContactIndex) {
            binding.apply {
                tvIndexLetter.text = item.letter
            }
        }
    }

    inner class PersonInfoHolder(private val binding: PersonInfoHolderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Contact.SweetiesID) {
            binding.apply {
                with(item.value) {
                    ivSweetiePhoto.setImageResource(imgSrc)
                    tvSweetieName.text = name
                    pbHeart.progress = heart
                    tvHeart.text = heart.toString() + "%"
                }

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java).apply {
                        putExtra("sweetieId", item.key)
                    }
                    startActivity(itemView.context, intent, null)
                }
            }
        }
    }

    companion object {
        const val VIEW_TYPE_HEADER = 1
        const val VIEW_TYPE_LIST = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_HEADER -> {
                val binding =
                    IndexHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                IndexHolder(binding)
            }

            VIEW_TYPE_LIST -> {
                val binding = PersonInfoHolderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                PersonInfoHolder(binding)
            }

            else -> throw IllegalArgumentException("Invalid View Type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is Contact.ContactIndex -> {
                val indexHolder = holder as IndexHolder
                indexHolder.bind(item)
            }

            is Contact.SweetiesID -> {
                val personInfoHolder = holder as PersonInfoHolder
                personInfoHolder.bind(item)
            }

            else -> throw IllegalArgumentException("Invalid View Type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is Contact.ContactIndex -> VIEW_TYPE_HEADER
            is Contact.SweetiesID -> VIEW_TYPE_LIST
            else -> throw IllegalArgumentException("Invalid View Type")
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                return FilterResults().apply {
                    values = if (charSequence.isNullOrBlank()) contactData else onFilter(
                        contactData,
                        charSequence.toString()
                    )
                }
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(
                charSequence: CharSequence?,
                filterResults: FilterResults
            ) {
                val results = filterResults.values as ArrayList<Contact>
                submitList(results)
            }
        }
    }

    private fun onFilter(list: List<Contact>, charString: String): List<Contact> {
        return list.filter { item ->
            item is Contact.SweetiesID &&
                    if (charString.all { it in 'a'..'z' || it in 'A'..'Z' }) item.value.name.lowercase()
                        .contains(charString.lowercase())
                    else KoreanMatcher.matchKoreanAndConsonant(item.value.name, charString)
        }
    }
}