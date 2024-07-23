package com.example.sweetcontactget.Dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.example.sweetcontactget.databinding.DialogAddContactBinding

class MainDialog(context: Context) : Dialog(context){
    private lateinit var binding : DialogAddContactBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogAddContactBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun initViews() = with(binding) {
        setCancelable(false)

        btnAddContactSave.setOnClickListener {

        }
    }
}