import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.Window
import com.example.sweetcontactget.databinding.DialogDatePickerBinding
import java.util.*

class CustomDatePickerDialog(
    context: Context,
    private val listener: (year: Int, month: Int, dayOfMonth: Int) -> Unit
) : Dialog(context) {

    private var _binding: DialogDatePickerBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = DialogDatePickerBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val calendar = Calendar.getInstance()
        binding.dpPickerDatePicker.init(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH),
            null
        )

        binding.dpPickerDatePicker.setCalendarViewShown(false)

        binding.btnPickerDateConfirm.setOnClickListener {
            listener(
                binding.dpPickerDatePicker.year,
                binding.dpPickerDatePicker.month,
                binding.dpPickerDatePicker.dayOfMonth
            )
            dismiss()
        }

        binding.btnPickerDateCancle.setOnClickListener {
            dismiss()
        }

        // Set the dialog size after the dialog has been created
        window?.let {
            setDialogSize(it, 0.8f, 0.35f)
        }
    }

    private fun setDialogSize(window: Window, widthRatio: Float, heightRatio: Float) {
        val displayMetrics = DisplayMetrics()
        window.windowManager.defaultDisplay.getMetrics(displayMetrics)

        val width = (displayMetrics.widthPixels * widthRatio).toInt()
        val height = (displayMetrics.heightPixels * heightRatio).toInt()

        window.setLayout(width, height)
    }
}
