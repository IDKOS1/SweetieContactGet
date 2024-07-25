package com.example.sweetcontactget.fragments.RandomCall

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sweetcontactget.R
import com.example.sweetcontactget.adapter.RandomViewPageAdapter
import com.example.sweetcontactget.databinding.FragmentRandomBinding
import com.google.android.material.tabs.TabLayoutMediator

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class RandomFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentRandomBinding? = null
    private val binding get() = _binding!!

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

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RandomFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}