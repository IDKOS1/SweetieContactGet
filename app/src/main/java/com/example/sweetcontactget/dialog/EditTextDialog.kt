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
//        dialogResize(context,this@EditTextDialog, 0.8f, 0.2f)

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
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = DialogMyPageEditTextBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        setCancelable(false)
//        dialogResize(context,this@EditTextDialog, 0.8f, 0.2f)
//
//        binding.run {
//
//            btnEditSave.setOnClickListener {
//                dismiss()
//            }
//
//            btnEditCancel.setOnClickListener {
//                cancel()
//            }
//        }
//    }
//
//    fun dialogResize(context: Context, dialog: Dialog, width: Float, height: Float){
//        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
//
//        if (Build.VERSION.SDK_INT < 30){
//            val display = windowManager.defaultDisplay
//            val size = Point()
//
//            display.getSize(size)
//
//            val window = dialog.window
//
//            val x = (size.x * width).toInt()
//            val y = (size.y * height).toInt()
//
//            window?.setLayout(x, y)
//
//        }else{
//            val rect = windowManager.currentWindowMetrics.bounds
//
//            val window = dialog.window
//            val x = (rect.width() * width).toInt()
//            val y = (rect.height() * height).toInt()
//
//            window?.setLayout(x, y)
//        }
//    }
}