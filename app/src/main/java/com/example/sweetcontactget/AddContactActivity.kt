package com.example.sweetcontactget

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.sweetcontactget.data.DataObject
import com.example.sweetcontactget.data.SweetieInfo
import com.example.sweetcontactget.databinding.ActivityAddContactBinding
import com.example.sweetcontactget.util.Util.initSpinner
import kotlin.random.Random
import kotlin.random.nextInt

class AddContactActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddContactBinding
    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddContactBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.tvAddContactAddPhoneNumber.setOnClickListener {
            count++
            if (count == 1) {
                binding.clAddContactPhoneNumber2.visibility = View.VISIBLE
                binding.tvAddContactDeletePhoneNumber.visibility = View.VISIBLE
            }

            if (count == 2) {
                binding.clAddContactPhoneNumber3.visibility = View.VISIBLE
                binding.tvAddContactAddPhoneNumber.visibility = View.GONE
            }
        }

        binding.tvAddContactDeletePhoneNumber.setOnClickListener {
            count--
            if (count == 1) {
                binding.clAddContactPhoneNumber3.visibility = View.GONE
                binding.tvAddContactAddPhoneNumber.visibility = View.VISIBLE
            }

            if (count == 0) {
                binding.clAddContactPhoneNumber2.visibility = View.GONE
                binding.tvAddContactDeletePhoneNumber.visibility = View.GONE
            }
        }

        initSpinner(this@AddContactActivity, binding.groupSpinner)

        binding.btnAddContactSave.setOnClickListener {
            var sweetieInfo = SweetieInfo(
                imgSrc = binding.ivAddContactImage.drawable,
                name = binding.etAddContactName.text.toString(),
                number = binding.etAddContactPhoneNumber.text.toString(),
                relationship = binding.groupSpinner.selectedItem.toString(),
                memo = binding.etAddContactMemo.text.toString(),
                heart = 0,
                isMarked = false
            )
            DataObject.addSweetieInfo(sweetieInfo)
            finish()
        }

        binding.btnAddContactCancel.setOnClickListener {
            val intent = Intent(this@AddContactActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.ivAddContactImage.setOnClickListener {
            val random = Random.nextInt(1..56)
            val currentId = random.let { DataObject.getSweetieInfo(it) }

            currentId.let {
                binding.ivAddContactImage.setImageDrawable(it.imgSrc)
            }
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }



}