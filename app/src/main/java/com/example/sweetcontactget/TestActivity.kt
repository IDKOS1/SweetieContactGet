package com.example.sweetcontactget

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.sweetcontactget.databinding.ActivityTestBinding
import com.example.sweetcontactget.dialog.EditTextDialog
import com.example.sweetcontactget.dialog.RandomCallDialog
import com.example.sweetcontactget.dialog.ThemeDialog

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
            val intent = Intent(this@TestActivity,AddContactActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.textView2.setOnClickListener {
            val dialog = ThemeDialog(this@TestActivity)
            dialog.show()
        }

        binding.textView3.setOnClickListener {
            val dialog = EditTextDialog(this@TestActivity)
            dialog.show("test","test")
        }

        binding.button.setOnClickListener {
            val dialog = RandomCallDialog(this@TestActivity)
            dialog.show()
        }
    }
}