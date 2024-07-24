package com.example.sweetcontactget

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.sweetcontactget.dialog.EditTextDialog
import com.example.sweetcontactget.dialog.MainDialog
import com.example.sweetcontactget.dialog.RandomCallDialog
import com.example.sweetcontactget.dialog.ThemeDialog
import com.example.sweetcontactget.databinding.ActivityTestBinding

class TestActivity : AppCompatActivity() {
    private lateinit var binding : ActivityTestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.textView.setOnClickListener {
            val dialog = MainDialog(this@TestActivity)
            dialog.show()
        }

        binding.textView2.setOnClickListener {
            val dialog = ThemeDialog(this@TestActivity)
            dialog.show()
        }

        binding.textView3.setOnClickListener {
            val dialog = EditTextDialog(this@TestActivity)
            dialog.show()
        }

        binding.button.setOnClickListener {
            val dialog = RandomCallDialog(this@TestActivity)
            dialog.show()
        }
    }
}