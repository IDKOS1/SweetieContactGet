package com.example.sweetcontactget.dialog

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import com.example.sweetcontactget.data.DataObject
import com.example.sweetcontactget.databinding.DialogRandomCallBinding
import com.example.sweetcontactget.util.Util
import kotlin.random.Random
import kotlin.random.nextInt

class RandomCallDialog(context: Context):Dialog(context) {
    private lateinit var binding: DialogRandomCallBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogRandomCallBinding.inflate(layoutInflater)
        setCancelable(false)
        setContentView(binding.root)
        dialogResize(context,this@RandomCallDialog, 0.9f, 0.4f)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val random = Random.nextInt(1..56)
        val currentId = random.let { DataObject.getSweetieInfo(it) }
        currentId.let {
            binding.run {
                ivRandomCallImage.setImageDrawable(it.imgSrc)
                tvRandomCallName.text = it.name
                tvRandomCallQuestion.text = "[ " + it.name + " ]짱 에게 전화를 거시겠습니까?"
                rbRandomCallHeart.rating = it.heart / 20.toFloat()
            }
        }


        binding.tvRandomCallMakeACall.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data =  Uri.parse("tel :"+Util.callSweetie(this.context,currentId.number))
        }
        binding.tvRandomCallMakeACallCancel.setOnClickListener {
            cancel()
        }
    }

    fun dialogResize(context: Context, dialog: Dialog, width: Float, height: Float) {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

        if (Build.VERSION.SDK_INT < 30) {
            val display = windowManager.defaultDisplay
            val size = Point()

            display.getSize(size)

            val window = dialog.window

            val x = (size.x * width).toInt()
            val y = (size.y * height).toInt()

            window?.setLayout(x, y)

        } else {
            val rect = windowManager.currentWindowMetrics.bounds

            val window = dialog.window
            val x = (rect.width() * width).toInt()
            val y = (rect.height() * height).toInt()

            window?.setLayout(x, y)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        cancel()
    }
}