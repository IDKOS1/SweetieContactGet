package com.example.sweetcontactget.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.sweetcontactget.fragments.contact.AllContactFragment
import com.example.sweetcontactget.fragments.contact.BookmarkFragment

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    val fragments: List<Fragment> = listOf(AllContactFragment(), BookmarkFragment())

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}
