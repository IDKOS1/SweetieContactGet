package com.example.sweetcontactget.fragments.contact

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sweetcontactget.adapter.ContactAdapter
import com.example.sweetcontactget.R
import com.example.sweetcontactget.data.DataObject.bookmarkData
import com.example.sweetcontactget.data.SweetieInfo
import com.example.sweetcontactget.databinding.FragmentBookmarkBinding
import com.example.sweetcontactget.util.CustomDividerDecoration
import com.example.sweetcontactget.util.KoreanMatcher.groupByIndex
import com.example.sweetcontactget.util.Util

class BookmarkFragment : Fragment(), ContactFragment.LayoutManagerSwitchable {
    private var _binding: FragmentBookmarkBinding? = null
    private val binding get() = _binding!!
    private lateinit var bookmarkAdapter: ContactAdapter
    private lateinit var rvBookmark: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        rvBookmark = binding.rvBookmark
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        if (::bookmarkAdapter.isInitialized) bookmarkAdapter.submitList(groupByIndex(bookmarkData as MutableMap<Int, SweetieInfo>))
    }

    private fun initView() = with(binding) {
        bookmarkAdapter = ContactAdapter(requireContext())
        rvBookmark.apply {
            adapter = bookmarkAdapter
            layoutManager = LinearLayoutManager(this.context)
            val dividerColor = ContextCompat.getColor(context, R.color.secondary)
            val itemDecoration =
                CustomDividerDecoration(context, height = 3f, dividerColor, 0f, 100f)
            addItemDecoration(itemDecoration)
        }
    }

    override fun updateLayoutManager(isGridLayout: Boolean) {
        Util.switchLayoutManager(requireContext(), rvBookmark, bookmarkAdapter, isGridLayout)
    }
}