package com.example.sweetcontactget.Fragments.Contact

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sweetcontactget.Adapter.ContactAdapter
import com.example.sweetcontactget.Data.DataObject.contactData
import com.example.sweetcontactget.R
import com.example.sweetcontactget.databinding.FragmentAllContactBinding

// TODO: Rename parameter arguments, choose names that match
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AllContactFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val contactAdapter by lazy { ContactAdapter().apply { submitList(contactData.toList()) } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentAllContactBinding.inflate(inflater, container, false)

        val recyclerView = binding.rvAllContactFragment


        recyclerView.apply {
            adapter = contactAdapter
            layoutManager = LinearLayoutManager(this.context)
            val itemDecoration = DividerItemDecoration(this.context,DividerItemDecoration.VERTICAL)
            val customDivider = ContextCompat.getDrawable(this.context, R.drawable.custom_divider)
            if(customDivider != null){
                itemDecoration.setDrawable(customDivider)
            }
            addItemDecoration(itemDecoration)
        }





        return binding.root
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AllContactFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    fun search(searchTarget: String) {
        contactAdapter.filter.filter(searchTarget)
    }
}