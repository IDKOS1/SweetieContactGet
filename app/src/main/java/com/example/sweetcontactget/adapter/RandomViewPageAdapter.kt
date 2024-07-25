package com.example.sweetcontactget.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.sweetcontactget.fragments.randomCall.RandomCallFragment
import com.example.sweetcontactget.fragments.randomCall.RecentRancdomCallFragment

class RandomViewPageAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> RandomCallFragment()
            1 -> RecentRancdomCallFragment()
            else -> throw IllegalArgumentException("Invalid position $position")
        }
    }
}