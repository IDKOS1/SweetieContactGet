import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.example.sweetcontactget.databinding.DialogDatePickerBinding
import java.util.*

class CustomDatePickerDialog(
    private val listener: (year: Int, month: Int, dayOfMonth: Int) -> Unit
) : DialogFragment() {

    private var _binding: DialogDatePickerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireContext())

        _binding = DialogDatePickerBinding.inflate(LayoutInflater.from(context))

        dialog.setContentView(binding.root)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        val calendar = Calendar.getInstance()
        binding.dpPickerDatePicker.init(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH),
            null
        )

        binding.dpPickerDatePicker.setCalendarViewShown(false)

        binding.btnPickerConfirm.setOnClickListener {
            listener(
                binding.dpPickerDatePicker.year,
                binding.dpPickerDatePicker.month,
                binding.dpPickerDatePicker.dayOfMonth
            )
            dialog.dismiss()
        }

        binding.btnPickerCancle.setOnClickListener {
            dialog.dismiss()
        }

        return dialog
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
