package com.example.sweetcontactget.Dialog

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.example.sweetcontactget.Data.Contact
import com.example.sweetcontactget.Data.DataObject
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