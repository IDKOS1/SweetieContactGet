package com.example.sweetcontactget.Adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.sweetcontactget.Fragments.Contact.AllContactFragment
import com.example.sweetcontactget.Fragments.Contact.BookmarkFragment

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    val fragments = mutableListOf(AllContactFragment(), BookmarkFragment())

    override fun getItemCount() = 2

    override fun createFragment(position: Int) = when (position) {
        in 0..1 -> fragments[position]
        else -> throw IllegalStateException("Unexpected position: $position")
    }
}