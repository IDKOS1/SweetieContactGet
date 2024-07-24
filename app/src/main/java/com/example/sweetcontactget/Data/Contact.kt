package com.example.sweetcontactget.Data

sealed class Contact {
    data class SweetiesID(val key: Int, val value: SweetieInfo) : Contact()
    data class ContactIndex(
        val letter: String
    ) : Contact()

    data class SweetieInfo(
        val imgSrc: Int,
        var name: String,
        var number: String,
        var relationship: String,
        var memo: String,
        var heart: Int,
        var isMarked: Boolean
    ) : Contact()
}


