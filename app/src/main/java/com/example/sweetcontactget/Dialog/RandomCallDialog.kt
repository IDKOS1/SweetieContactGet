package com.example.sweetcontactget.Dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.example.sweetcontactget.databinding.DialogRandomCallBinding

class RandomCallDialog(context: Context):Dialog(context) {
    private lateinit var binding: DialogRandomCallBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogRandomCallBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setCancelable(false)

        binding.tvRandomCallMakeACallCancel.setOnClickListener {
            cancel()
        }
    }
}