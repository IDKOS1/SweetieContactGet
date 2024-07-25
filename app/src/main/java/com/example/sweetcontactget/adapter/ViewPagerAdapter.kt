package com.example.sweetcontactget.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.sweetcontactget.fragments.contact.AllContactFragment
import com.example.sweetcontactget.fragments.contact.BookmarkFragment

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    val fragments = mutableListOf(AllContactFragment(), BookmarkFragment())

    override fun getItemCount() = 2

    override fun createFragment(position: Int) = when (position) {
        in 0..1 -> fragments[position]
        else -> throw IllegalStateException("Unexpected position: $position")
    }
}