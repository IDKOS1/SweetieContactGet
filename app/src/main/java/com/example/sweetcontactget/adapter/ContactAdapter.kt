package com.example.sweetcontactget.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
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
import com.example.sweetcontactget.databinding.PersonInfoHolderGridBinding
import com.example.sweetcontactget.util.ItemTouchHelperCallback
import com.example.sweetcontactget.util.Util

class ContactAdapter(private val context: Context) :
    ListAdapter<Contact, RecyclerView.ViewHolder>(object : DiffUtil.ItemCallback<Contact>() {
        override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem == newItem
        }
    }), Filterable, ItemTouchHelperCallback.ItemTouchHelperListener {

        private var viewType = VIEW_TYPE_LIST_LINEAR

    fun setViewType(viewType: Int){
        this.viewType = viewType
        notifyDataSetChanged()
    }

    class IndexHolder(private val binding: IndexHolderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Contact.ContactIndex) {
            binding.apply {
                tvIndexLetter.text = item.letter
            }
        }
    }

    class PersonInfoHolder(private val binding: PersonInfoHolderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Contact.SweetiesID) {
            binding.apply {
                with(item.value) {
                    ivSweetiePhoto.setImageDrawable(imgSrc)
                    tvSweetieName.text = name
                    pbHeart.progress = heart
                    tvHeart.text = heart.toString() + "%"
                    ivBehindView.setImageDrawable(imgSrc)
                }


                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java).apply {
                        putExtra("sweetieId", item.key)
                    }
                    startActivity(itemView.context, intent, null)
                }
            }
        }

        val frontView = binding.clPersonInfoHolderSize
        val behindView = binding.clBehindView

    }

    class PersonInfoGridHolder(private val binding: PersonInfoHolderGridBinding):
    RecyclerView.ViewHolder(binding.root){
        fun bind(item: Contact.SweetiesID){
            binding.apply {
                with(item.value){
                    ivSweetiePhotoGrid.setImageDrawable(imgSrc)
                    tvSweetieNameGrid.text = name
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
        const val VIEW_TYPE_LIST_LINEAR = 2
        const val VIEW_TYPE_LIST_GRID = 3
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_HEADER -> {
                val binding =
                    IndexHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                IndexHolder(binding)
            }

            VIEW_TYPE_LIST_LINEAR -> {
                val binding = PersonInfoHolderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                PersonInfoHolder(binding)
            }

            VIEW_TYPE_LIST_GRID -> {
                val binding = PersonInfoHolderGridBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                PersonInfoGridHolder(binding)
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
                if (viewType == VIEW_TYPE_LIST_LINEAR){
                    val personInfoHolder = holder as PersonInfoHolder
                    personInfoHolder.bind(item)
                }else{
                    val personInfoGridHolder = holder as PersonInfoGridHolder
                    personInfoGridHolder.bind(item)
                }

            }

            else -> throw IllegalArgumentException("Invalid View Type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is Contact.ContactIndex -> VIEW_TYPE_HEADER
            is Contact.SweetiesID -> if(viewType == VIEW_TYPE_LIST_LINEAR) VIEW_TYPE_LIST_LINEAR else VIEW_TYPE_LIST_GRID
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

    override fun onItemSwipe(position: Int) {
        val item = getItem(position)
        if (item is Contact.SweetiesID) {
            Util.callSweetie(context, item.value.number)
        }

        notifyItemChanged(position)
    }
}