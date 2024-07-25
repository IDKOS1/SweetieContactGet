package com.example.sweetcontactget.util

import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.sweetcontactget.adapter.ContactAdapter

class ItemTouchHelperCallback(private val listener: ItemTouchHelperListener): ItemTouchHelper.Callback() {

    interface ItemTouchHelperListener {
        fun onItemSwipe(position: Int)
    }

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val position = viewHolder.adapterPosition
        val adapter = recyclerView.adapter as ContactAdapter
        val viewType = adapter.getItemViewType(position)
        return if (viewType == ContactAdapter.VIEW_TYPE_HEADER){
            makeMovementFlags(0,0)
        }else{
            val swipeFlags = ItemTouchHelper.RIGHT
            makeMovementFlags(0,swipeFlags)
        }

    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        listener.onItemSwipe(viewHolder.adapterPosition)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
            val itemView = (viewHolder as ContactAdapter.PersonInfoHolder).itemView
            val frontView = (viewHolder as ContactAdapter.PersonInfoHolder).frontView
            getDefaultUIUtil().onDraw(c, recyclerView, frontView, dX, dY, actionState, isCurrentlyActive)
        }else{
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }
    }

    override fun onChildDrawOver(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder?,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE && viewHolder != null) {
            val itemView = viewHolder.itemView
            val behindView = (viewHolder as ContactAdapter.PersonInfoHolder).behindView

            if (dX > 0) { // Swiping to the right
                behindView.translationX = dX
            } else if (dX == 0f) {
                behindView.translationX = 0f
            }

            getDefaultUIUtil().onDrawOver(c, recyclerView, (viewHolder as ContactAdapter.PersonInfoHolder).frontView, dX, dY, actionState, isCurrentlyActive)
        } else {
            super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        val behindView = (viewHolder as ContactAdapter.PersonInfoHolder).behindView
        behindView.translationX = -viewHolder.itemView.width.toFloat()
        val frontView = (viewHolder as ContactAdapter.PersonInfoHolder).frontView
        frontView.translationX = 0f
    }




}