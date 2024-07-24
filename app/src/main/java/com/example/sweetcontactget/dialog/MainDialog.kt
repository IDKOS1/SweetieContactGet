package com.example.sweetcontactget.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import com.example.sweetcontactget.databinding.DialogAddContactBinding

class MainDialog(context: Context) : Dialog(context){
    private lateinit var binding : DialogAddContactBinding
    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogAddContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setCancelable(false)

        binding.tvAddContactAddPhoneNumber.setOnClickListener {
                count++
            if(count==1)
                binding.clAddContactPhoneNumber2.visibility = View.VISIBLE

            if(count==2)
                binding.clAddContactPhoneNumber3.visibility = View.VISIBLE
        }

        binding.btnAddContactSave.setOnClickListener {
        }

        binding.btnAddContactCancel.setOnClickListener {
            cancel()
        }
    }
}