package com.example.sweetcontactget.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import com.example.sweetcontactget.databinding.DialogNotificationBinding
import java.time.LocalDateTime
import java.util.Calendar

class NotificationDialog(
    context: Context,
    private val listener: (notificationDate: LocalDateTime) -> Unit
) : Dialog(context) {

    private var _binding: DialogNotificationBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = DialogNotificationBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // Set the dialog size
        setDialogSize(0.95f, 0.85f)

        val calendar = Calendar.getInstance()
        binding.dpNotificationDatePicker.init(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH),
            null
        )

        binding.btnNotificationConfirm.setOnClickListener {
            val notificationDate = LocalDateTime.of(
                binding.dpNotificationDatePicker.year,
                binding.dpNotificationDatePicker.month + 1,
                binding.dpNotificationDatePicker.dayOfMonth,
                binding.tpNotificationTimePicker.hour,
                binding.tpNotificationTimePicker.minute
            )
            listener(
                notificationDate
            )
            dismiss()
        }

        binding.btnNotificationCancle.setOnClickListener {
            dismiss()
        }
    }

    private fun setDialogSize(widthRatio: Float, heightRatio: Float) {
        val window = window ?: return

        val displayMetrics = DisplayMetrics()
        window.windowManager.defaultDisplay.getMetrics(displayMetrics)

        val width = (displayMetrics.widthPixels * widthRatio).toInt()
        val height = (displayMetrics.heightPixels * heightRatio).toInt()

        window.setLayout(width, height)
    }
}
