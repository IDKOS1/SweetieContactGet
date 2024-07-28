package com.example.sweetcontactget.fragments.contact

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sweetcontactget.adapter.ContactAdapter
import com.example.sweetcontactget.data.Contact
import com.example.sweetcontactget.R
import com.example.sweetcontactget.data.DataObject.contactList
import com.example.sweetcontactget.databinding.FragmentAllContactBinding
import com.example.sweetcontactget.util.CustomDividerDecoration
import com.example.sweetcontactget.util.ItemTouchHelperCallback
import com.example.sweetcontactget.util.TopSnappedSmoothScroller
import com.example.sweetcontactget.util.Util
import com.reddit.indicatorfastscroll.FastScrollItemIndicator
import com.reddit.indicatorfastscroll.FastScrollerView

// TODO: Rename parameter arguments, choose names that match
private const val ARG_PARAM1 = "param1"

class AllContactFragment : Fragment(),ContactFragment.LayoutManagerSwitchable {
    private var param1: Int? = null
    private var _binding: FragmentAllContactBinding? = null
    private val binding get() = _binding!!
    private lateinit var contactAdapter: ContactAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllContactBinding.inflate(inflater, container, false)
        recyclerView = binding.rvAllContactFragment

        contactAdapter = ContactAdapter(requireContext()).apply {
            submitList(contactList.toList())
        }

        recyclerView.apply {
            adapter = contactAdapter
            layoutManager = LinearLayoutManager(this.context)
            ContactAdapter.itemClickListener = object : ContactAdapter.ItemClickListener {
                override fun onItemLongClick(isShow: Boolean) {
                    (parentFragment as ContactFragment).handleToolbarVisibility(isShow)
                }
            }
            val dividerColor = ContextCompat.getColor(context, R.color.secondary)
            val itemDecoration =
                CustomDividerDecoration(context, height = 3f, dividerColor, 0f, 100f)
            addItemDecoration(itemDecoration)

            val itemTouchHelperCallback = ItemTouchHelperCallback(contactAdapter)
            val helper = ItemTouchHelper(itemTouchHelperCallback)
            helper.attachToRecyclerView(recyclerView)
        }


        // 패스트 스크롤 정의
        binding.fastscroller.setupWithRecyclerView(recyclerView, { position ->
            val item = contactList.getOrNull(position)
            if (item != null && item is Contact.ContactIndex) {
//                Log.d("FastScroller","Indicator: ${item.letter} at position $position")
                FastScrollItemIndicator.Text(item.letter)
            } else {
//                Log.d("FastScroller", "No Indicator at position $position")
                null
            }
        })

        binding.fastscrollerThumb.setupWithFastScroller(binding.fastscroller)


        //패스트 스크롤 동작 커스텀
        binding.fastscroller.apply {
            useDefaultScroller = false
            itemIndicatorSelectedCallbacks += object :
                FastScrollerView.ItemIndicatorSelectedCallback {
                override fun onItemIndicatorSelected(
                    indicator: FastScrollItemIndicator,
                    indicatorCenterY: Int,
                    itemPosition: Int
                ) {
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val smoothScroller = TopSnappedSmoothScroller(recyclerView.context)
                    smoothScroller.targetPosition = itemPosition
                    layoutManager.startSmoothScroll(smoothScroller)

                }
            }
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }


    fun search(searchTarget: CharSequence?) {
        if (::contactAdapter.isInitialized) contactAdapter.filter.filter(searchTarget)
    }

    fun refresh() {
        contactAdapter.submitList(contactList.toList())
        contactAdapter.notifyItemRangeChanged(0, contactList.size)
        contactAdapter.notifyDataSetChanged()
    }

    override fun updateLayoutManager(isGridLayout : Boolean){
        Util.switchLayoutManager(requireContext(),recyclerView,contactAdapter,isGridLayout)
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: Int) =
            AllContactFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                }
            }
    }
}