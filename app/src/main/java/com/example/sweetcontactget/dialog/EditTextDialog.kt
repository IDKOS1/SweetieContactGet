package com.example.sweetcontactget.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.example.sweetcontactget.R
import com.example.sweetcontactget.data.formatPhoneNumber
import com.example.sweetcontactget.data.isRegularName
import com.example.sweetcontactget.data.isRegularPhoneNumber
import com.example.sweetcontactget.databinding.DialogMyPageEditTextBinding

class EditTextDialog(private val context: Context) {
    private lateinit var binding: DialogMyPageEditTextBinding
    private val dialog = Dialog(context)

    fun show(type: String, text: String, editText: String) {
        when (type) {
            context.getString(R.string.name) -> {
                var isNameCorrect = true

                dialog.show()

                binding = DialogMyPageEditTextBinding.inflate(dialog.layoutInflater)
                dialog.setContentView(binding.root)
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.setCancelable(false)
                dialog.window!!.setLayout(900, 500)

                binding.tvEditName.text = text
                binding.etEditName.hint = editText
                binding.tvEditWrongName.text = context.getString(R.string.dialog_wrong_message_name)

                binding.etEditName.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        if (isRegularName(binding.etEditName.text.toString().trim())) {
                            binding.tvEditWrongName.visibility = View.INVISIBLE
                            isNameCorrect = true
                        } else {
                            binding.tvEditWrongName.visibility = View.VISIBLE
                            isNameCorrect = false
                        }
                    }

                    override fun afterTextChanged(s: Editable?) {}
                })

                binding.run {
                    btnEditSave.setOnClickListener {
                        if (isNameCorrect) {
                            onClickedListener.onClicked(etEditName.text.toString())
                            dialog.dismiss()
                        } else {
                            Toast.makeText(
                                context,
                                context.getString(R.string.invalid_input), Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    btnEditCancel.setOnClickListener {
                        dialog.cancel()
                    }
                }
            }

            context.getString(R.string.number) -> {
                var isNumberCorrect = true

                dialog.show()

                binding = DialogMyPageEditTextBinding.inflate(dialog.layoutInflater)
                dialog.setContentView(binding.root)
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.setCancelable(false)
                dialog.window!!.setLayout(900, 500)

                binding.tvEditName.text = text
                binding.etEditName.hint = editText
                binding.tvEditWrongName.text =
                    context.getString(R.string.dialog_wrong_message_number)

                binding.etEditName.addTextChangedListener(object : TextWatcher {
                    private var isFormat: Boolean = false
                    private var isDeleteHyphen: Boolean = false

                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                        if (count > 0 && s?.get(start) == '-') {
                            isDeleteHyphen = true
                        }
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        if (isRegularPhoneNumber(binding.etEditName.text.toString().trim())) {
                            binding.tvEditWrongName.visibility = View.INVISIBLE
                            isNumberCorrect = true
                        } else {
                            binding.tvEditWrongName.visibility = View.VISIBLE
                            isNumberCorrect = false
                        }
                    }

                    override fun afterTextChanged(s: Editable?) {
                        if (isFormat || isDeleteHyphen) return
                        isFormat = true

                        val replacedText = s.toString().replace("-", "")
                        val formattedNumber = formatPhoneNumber(replacedText)

                        s?.replace(0, s.length, formattedNumber)
                        isFormat = false
                    }
                })

                binding.run {
                    btnEditSave.setOnClickListener {
                        if (isNumberCorrect) {
                            onClickedListener.onClicked(etEditName.text.toString())
                            dialog.dismiss()
                        } else {
                            Toast.makeText(
                                context,
                                context.getString(R.string.invalid_input),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    btnEditCancel.setOnClickListener {
                        dialog.cancel()
                    }
                }
            }

            context.getString(R.string.alltext) -> {
                dialog.show()

                binding = DialogMyPageEditTextBinding.inflate(dialog.layoutInflater)
                dialog.setContentView(binding.root)
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.setCancelable(false)
                dialog.window!!.setLayout(900, 500)

                binding.tvEditName.text = text
                binding.etEditName.hint = editText
                binding.tvEditWrongName.visibility = View.INVISIBLE

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
        }


    }

    interface ButtonClickListener {
        fun onClicked(content: String)
    }

    private lateinit var onClickedListener: ButtonClickListener

    fun setOnClickedListener(listener: ButtonClickListener) {
        onClickedListener = listener
    }
}