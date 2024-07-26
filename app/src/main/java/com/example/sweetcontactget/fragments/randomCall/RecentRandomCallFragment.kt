package com.example.sweetcontactget.fragments.randomCall

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sweetcontactget.data.DataObject
import com.example.sweetcontactget.databinding.FragmentRecentRancdomCallBinding

class RecentRandomCallFragment : Fragment() {

    private var _binding: FragmentRecentRancdomCallBinding? = null
    private val binding get() = _binding!!

    private val recentListViewAdapter by lazy { RecentRandomCallListViewAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecentRancdomCallBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvRecentList.adapter = recentListViewAdapter
        binding.rvRecentList.layoutManager = LinearLayoutManager(this.context)
        recentListViewAdapter.submitList(DataObject.randomCallList)
    }

    override fun onStart() {
        super.onStart()

        recentListViewAdapter.notifyDataSetChanged()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}