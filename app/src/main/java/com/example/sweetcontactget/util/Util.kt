package com.example.sweetcontactget.util

import android.content.Context
import android.content.Intent
import android.net.Uri

object Util {
    fun sendMessage(context: Context, phoneNumber: String) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("sms:$phoneNumber")
        }
        context.startActivity(intent)
    }

    fun callSweetie(context: Context, phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        context.startActivity(intent)
    }
}