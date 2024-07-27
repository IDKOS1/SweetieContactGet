package com.example.sweetcontactget.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.example.sweetcontactget.R
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

    fun initSpinner(context: Context, spinner: Spinner) {
        ArrayAdapter.createFromResource(
            context, R.array.group_array, R.layout.group_item_spinner
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (parent != null) {
                    Toast.makeText(context, parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                return
            }

        }
    }
}