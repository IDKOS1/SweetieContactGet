package com.example.sweetcontactget.Fragments.RandomCall

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

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