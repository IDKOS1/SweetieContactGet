package com.example.sweetcontactget.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.example.sweetcontactget.databinding.DialogMyPageEditTextBinding
class EditTextDialog(context: Context) {
    private lateinit var binding : DialogMyPageEditTextBinding
    private val dialog = Dialog(context)

    fun show(text:String, editText : String){
        dialog.show()

        binding = DialogMyPageEditTextBinding.inflate(dialog.layoutInflater)
        dialog.setContentView(binding.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.window!!.setLayout(900, 500)

        binding.tvEditName.text = text
        binding.etEditName.hint = editText

        binding.run {
            btnEditSave.setOnClickListener {
                onClickedListener.onClicked(etEditName.text.toString())
                dialog.dismiss()
            }

            btnEditCancel.setOnClickListener {
                dialog.cancel()
            }
        }
    }

    interface ButtonClickListener {
        fun onClicked(myName: String)
    }

    private lateinit var onClickedListener: ButtonClickListener

    fun setOnClickedListener(listener: ButtonClickListener) {
        onClickedListener = listener
    }
}