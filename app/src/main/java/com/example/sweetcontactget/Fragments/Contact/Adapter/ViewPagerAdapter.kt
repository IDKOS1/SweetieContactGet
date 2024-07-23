package com.example.sweetcontactget.Fragments.Contact.Adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.sweetcontactget.Fragments.Contact.AllContactFragment
import com.example.sweetcontactget.Fragments.Contact.BookmarkFragment

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int) = when (position) {
        0 -> AllContactFragment()
        1 -> BookmarkFragment()
        else -> throw IllegalStateException("Unexpected position: $position")
    }
}