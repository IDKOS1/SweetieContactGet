package com.example.sweetcontactget.Fragments.Contact


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
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
    ): View? {
        val binding = FragmentAllContactBinding.inflate(inflater, container, false)

        val recyclerView = binding.rvAllContactFragment


        recyclerView.apply {
            adapter = ContactAdapter(contactData)
            layoutManager = LinearLayoutManager(this.context)

            val dividerColor = ContextCompat.getColor(context,R.color.secondary)
                val itemDecoration = CustomDividerDecoration(context, height = 3f, dividerColor,0f ,100f)
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
}