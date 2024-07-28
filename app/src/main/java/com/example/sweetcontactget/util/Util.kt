package com.example.sweetcontactget.util

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.ContactsContract
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.example.sweetcontactget.R
import com.example.sweetcontactget.data.DataObject
import com.example.sweetcontactget.data.SweetieInfo
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sweetcontactget.adapter.ContactAdapter


object Util {
    fun sendMessage(context: Context, sweetieId: Int, phoneNumber: String) {
        if (checkCallPermission(context)) {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("sms:$phoneNumber")
            }
            context.startActivity(intent)
            DataObject.increaseHeart(sweetieId, 10)
        } else {
            showToast(context, "권한 설정이 필요합니다.")
        }
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
                    if (sweetiesId != null) {
                        DataObject.editGroup(sweetiesId, position)

                    }
                }

                if (position == 0) {
                    val tv = view?.findViewById<TextView>(R.id.tvItemSpinner)
                    tv?.setTextColor(ContextCompat.getColor(context, R.color.gray))
                } else {
                    val tv = view?.findViewById<TextView>(R.id.tvItemSpinner)
                    tv?.setTextColor(ContextCompat.getColor(context, R.color.white))

                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                return
            }

        }
    }

    fun getRelationshipString(type: Int) = when (type) {
        ContactsContract.CommonDataKinds.Relation.TYPE_RELATIVE,
        ContactsContract.CommonDataKinds.Relation.TYPE_BROTHER,
        ContactsContract.CommonDataKinds.Relation.TYPE_SISTER,
        ContactsContract.CommonDataKinds.Relation.TYPE_CHILD,
        ContactsContract.CommonDataKinds.Relation.TYPE_FATHER,
        ContactsContract.CommonDataKinds.Relation.TYPE_MOTHER,
        ContactsContract.CommonDataKinds.Relation.TYPE_PARENT,
        ContactsContract.CommonDataKinds.Relation.TYPE_SPOUSE -> 1

        ContactsContract.CommonDataKinds.Relation.TYPE_FRIEND -> 4

        ContactsContract.CommonDataKinds.Relation.TYPE_MANAGER,
        ContactsContract.CommonDataKinds.Relation.TYPE_ASSISTANT -> 2

        else -> 0
    }

    fun switchLayoutManager(context: Context, recyclerView: RecyclerView, adapter: ContactAdapter, isGridLayout: Boolean) {
        if (isGridLayout) {
            recyclerView.layoutManager = GridLayoutManager(context, 3).apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return if (adapter.getItemViewType(position) == ContactAdapter.VIEW_TYPE_HEADER) {
                            3
                        } else {
                            1
                        }
                    }
                }
            }
            adapter.setViewType(ContactAdapter.VIEW_TYPE_LIST_GRID)
        } else {
            recyclerView.layoutManager = LinearLayoutManager(context)
            adapter.setViewType(ContactAdapter.VIEW_TYPE_LIST_LINEAR)
        }
        adapter.notifyDataSetChanged()
    }
}