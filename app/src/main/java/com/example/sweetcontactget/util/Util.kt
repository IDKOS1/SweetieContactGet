package com.example.sweetcontactget.util

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.example.sweetcontactget.R
import com.example.sweetcontactget.data.DataObject
import com.example.sweetcontactget.data.SweetieInfo
import androidx.core.content.ContextCompat
import com.example.sweetcontactget.data.DataObject


object Util {
    fun sendMessage(context: Context, phoneNumber: String) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("sms:$phoneNumber")
        }
        context.startActivity(intent)
    }

    fun callSweetie(context: Context, sweetieId: Int, phoneNumber: String) {
        if (checkCallPermission(context)) {
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:$phoneNumber")
            context.startActivity(intent)
            DataObject.increaseHeart(sweetieId, 20)
        } else {
            showToast(context, "권한 설정이 필요합니다.")
        }
    }

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun MutableMap<Int, SweetieInfo>.removeAll(keys: Set<Int>) {
        keys.forEach { this.remove(it) }
    }

    fun MutableMap<Int, SweetieInfo>.sortedByName() =
        this.toList().sortedBy { it.second.name }.toMap().toMutableMap()

    private fun checkCallPermission(context: Context): Boolean {
        val callPermission =
            ContextCompat.checkSelfPermission(context, android.Manifest.permission.CALL_PHONE)
        return callPermission == PackageManager.PERMISSION_GRANTED
    }


    fun initSpinner(context: Context, spinner: Spinner, sweetiesId: Int?) {
        ArrayAdapter.createFromResource(
            context, R.array.group_array, R.layout.group_item_spinner
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.item_spinner_dropdown)

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
                    if(sweetiesId != null){
                        DataObject.editGroup(sweetiesId, position)
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                return
            }

        }
    }
}