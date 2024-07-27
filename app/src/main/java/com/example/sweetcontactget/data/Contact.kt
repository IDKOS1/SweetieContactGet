package com.example.sweetcontactget.data

import android.graphics.drawable.Drawable

sealed class Contact {
    data class SweetiesID(val key: Int, val value: SweetieInfo) : Contact()
    data class ContactIndex(
        val letter: String
    ) : Contact()
}

data class SweetieInfo(
    var imgSrc: Drawable? = null,
    var name: String,
    var number: String,
    var relationship: Int,
    var memo: String,
    var heart: Int,
    var isMarked: Boolean,
) : Contact()
