package com.example.sweetcontactget.Data

import java.time.LocalDate

data class MyProfile(
    var imgSrc: Int,
    var name: String,
    var phone_number: String,
    var address: String,
    var birthday: LocalDate,
    var infoMessage: String,
)
