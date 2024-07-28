package com.example.sweetcontactget.fragments.contact

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.postDelayed
import com.example.sweetcontactget.AddContactActivity
import com.example.sweetcontactget.R
import com.example.sweetcontactget.adapter.ViewPagerAdapter
import com.example.sweetcontactget.data.DataObject.changedBookmark
import com.example.sweetcontactget.data.DataObject.deleteSweetieInfo
import com.example.sweetcontactget.data.DataObject.selectAllOrClear
import com.example.sweetcontactget.data.DataObject.selectedSet
import com.example.sweetcontactget.databinding.FragmentContactBinding
import com.google.android.material.tabs.TabLayoutMediator

class ContactFragment : Fragment() {
    private var _binding: FragmentContactBinding? = null
    private val binding get() = _binding!!
    private val vpAdapter: ViewPagerAdapter by lazy { ViewPagerAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initToolbar()

        binding.toggleSetLayout.setOnToggledListener { _, isOn ->
            updateLayoutForAllFragments(isOn)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateLayoutForAllFragments(isOn: Boolean) {
        vpAdapter.fragments.forEach { fragment ->
            if (fragment is LayoutManagerSwitchable) {
                if (fragment.isAdded) {
                    fragment.updateLayoutManager(isOn)
                } else {
                    fragment.view?.postDelayed(100) {
                        fragment.updateLayoutManager(isOn)
                    }
                }
            }
        }
    }

    private fun initView() = with(binding) {
        vpContactViewPager.adapter = vpAdapter
        TabLayoutMediator(tlContactTab, vpContactViewPager) { tab, position ->
            tab.text = when (position) {
                in 0..1 -> resources.getStringArray(R.array.contact_tab)[position]
                else -> throw IllegalStateException("Unexpected position: $position")
            }
        }.attach()

        etContactSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                (vpAdapter.fragments[0] as AllContactFragment).search(p0)
            }
        })

        fabContactAdd.setOnClickListener {
            startActivity(Intent(requireActivity(), AddContactActivity::class.java))
        }
    }

    fun handleToolbarVisibility(isShow: Boolean) {
        binding.llContactToolbar.visibility = if (isShow) View.VISIBLE else View.GONE
    }

    private fun initToolbar() {
        binding.tvSelectAllBtn.setOnClickListener {
            selectAllOrClear()
            (vpAdapter.fragments[0] as AllContactFragment).refresh()
        }
        binding.tvBookmarkBtn.setOnClickListener {
            changedBookmark(selectedSet)
            (vpAdapter.fragments[0] as AllContactFragment).refresh()
            handleToolbarVisibility(false)
            binding.vpContactViewPager.currentItem = 1
        }
        binding.tvDeleteBtn.setOnClickListener {
            deleteSweetieInfo(selectedSet)
            (vpAdapter.fragments[0] as AllContactFragment).refresh()
            (vpAdapter.fragments[1] as BookmarkFragment).onResume()
            handleToolbarVisibility(false)
        }
    }

    interface LayoutManagerSwitchable {
        fun updateLayoutManager(isGridLayout: Boolean)
    }
}
