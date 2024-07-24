package com.example.sweetcontactget.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.sweetcontactget.adapter.ContactAdapter.Companion.VIEW_TYPE_HEADER

class CustomDividerDecoration (context: Context,private val height : Float, private val color : Int, private val padding : Float, private val endMargin: Float) : RecyclerView.ItemDecoration(){

    private val divider : Drawable?
    private val paint = Paint()

    init {
        paint.color = color
        val a = context.obtainStyledAttributes(intArrayOf(android.R.attr.listDivider))
        divider = a.getDrawable(0)
        a.recycle()
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.paddingStart + padding

        for (i in 0 until parent.childCount){

            val child = parent.getChildAt(i)
            val adapterPosition = parent.getChildAdapterPosition(child)
            if(adapterPosition == RecyclerView.NO_POSITION || !shouldDrawDividerForPosition(adapterPosition, parent)){
                continue
            }
            val params = child.layoutParams as RecyclerView.LayoutParams

            val top = (child.bottom + params.bottomMargin).toFloat()
            val bottom = top + height
            val right = if(parent.adapter?.getItemViewType(adapterPosition)== VIEW_TYPE_HEADER){
                parent.width - parent.paddingEnd.toFloat()
            }else{
                parent.width - parent.paddingEnd.toFloat() - endMargin
            }

            c.drawRect(left,top,right,bottom,paint)
        }
    }

    private fun drawVertical(c: Canvas, parent: RecyclerView) {
        if (divider == null) return

        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        val childCount = parent.childCount
        for (i in 0 until childCount - 1) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin
            val bottom = top + divider.intrinsicHeight
            if (shouldDrawDividerForPosition(i, parent)) {
                divider.setBounds(left, top, right, bottom)
                divider.draw(c)
            }
        }
    }

    private fun shouldDrawDividerForPosition(position: Int, parent: RecyclerView): Boolean {
        val viewType = parent.adapter?.getItemViewType(position)
        val nextViewType =if(position + 1 <parent.adapter?.itemCount ?: 0){
            parent.adapter?.getItemViewType(position+1)
        }else{
            null
        }


        return viewType != VIEW_TYPE_HEADER && nextViewType != VIEW_TYPE_HEADER
    }


    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        if (position != RecyclerView.NO_POSITION && shouldDrawDividerForPosition(position, parent)) {
            outRect.set(0, 0, 0, height.toInt())
        } else {
            outRect.set(0, 0, 0, 0)
        }
    }



}



