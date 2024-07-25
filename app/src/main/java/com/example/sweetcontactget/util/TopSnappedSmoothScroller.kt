package com.example.sweetcontactget.util

import android.content.Context
import androidx.recyclerview.widget.LinearSmoothScroller

class TopSnappedSmoothScroller(context : Context) : LinearSmoothScroller(context){
    override fun getVerticalSnapPreference(): Int {
        return SNAP_TO_START
    }
}