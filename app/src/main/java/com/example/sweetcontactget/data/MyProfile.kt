package com.example.sweetcontactget.data

import java.time.LocalDate

data class MyProfile(
    var imgSrc: Int,
    var name: String,
    var phoneNumber: String,
    var address: String,
    var birthday: LocalDate,
    var infoMessage: String,
)
