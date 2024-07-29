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
    var secondNumber: String?,
    var thirdNumber : String?,
    var event : String = "최근 설정한 이벤트가 없습니다.",
    var relationship: Int,
    var memo: String,
    var heart: Int,
    var isMarked: Boolean,
    var isCleared: Boolean = false
) : Contact()
