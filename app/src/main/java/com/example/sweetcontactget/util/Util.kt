package com.example.sweetcontactget.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.example.sweetcontactget.data.SweetieInfo

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

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun MutableMap<Int, SweetieInfo>.removeAll(keys: Set<Int>) {
        keys.forEach { this.remove(it) }
    }

    fun MutableMap<Int, SweetieInfo>.sortedByName() =
        this.toList().sortedBy { it.second.name }.toMap().toMutableMap()
}