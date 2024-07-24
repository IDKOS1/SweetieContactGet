package com.example.sweetcontactget.Dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.example.sweetcontactget.Data.DataObject
import com.example.sweetcontactget.databinding.DialogRandomCallBinding
import kotlin.random.Random
import kotlin.random.nextInt

class RandomCallDialog(context: Context):Dialog(context) {
    private lateinit var binding: DialogRandomCallBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogRandomCallBinding.inflate(layoutInflater)


        setCancelable(false)
        setContentView(binding.root)
        dialogResize(context,this@RandomCallDialog, 1.0f, 0.4f)

        var random = Random.nextInt(1..56)
        binding.ivRandomCallImage.setImageResource(DataObject.contactMap[random]?.imgSrc as Int)
        binding.tvRandomCallName.text = DataObject.contactMap[random]?.name
        binding.tvRandomCallQuestion.text = "[ "+DataObject.contactMap[random]?.name+" ]에게 전화를 거시겠습니까?"

        binding.tvRandomCallMakeACall.setOnClickListener {

        }
        binding.tvRandomCallMakeACallCancel.setOnClickListener {
            cancel()
        }
    }

    fun dialogResize(context: Context, dialog: Dialog, width: Float, height: Float){
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

        if (Build.VERSION.SDK_INT < 30){
            val display = windowManager.defaultDisplay
            val size = Point()

            display.getSize(size)

            val window = dialog.window

            val x = (size.x * width).toInt()
            val y = (size.y * height).toInt()

            window?.setLayout(x, y)

        }else{
            val rect = windowManager.currentWindowMetrics.bounds

            val window = dialog.window
            val x = (rect.width() * width).toInt()
            val y = (rect.height() * height).toInt()

            window?.setLayout(x, y)
        }
    }
}