package com.example.sweetcontactget.fragments.randomCall

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sweetcontactget.R
import com.example.sweetcontactget.adapter.RandomViewPageAdapter
import com.example.sweetcontactget.databinding.FragmentRandomBinding
import com.google.android.material.tabs.TabLayoutMediator

class RandomFragment : Fragment() {

    private var _binding: FragmentRandomBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRandomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            randomViewPager.adapter = RandomViewPageAdapter(this@RandomFragment)
            TabLayoutMediator(randomTabs, randomViewPager) { tab, position ->
                tab.text = when (position) {
                    in 0..1 -> resources.getStringArray(R.array.random_tab)[position]
                    else -> throw IllegalStateException("Unexpected position: $position")
                }
            }.attach()

        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}