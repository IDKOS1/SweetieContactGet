package com.example.sweetcontactget

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.sweetcontactget.data.DataObject
import com.example.sweetcontactget.data.SweetieInfo
import com.example.sweetcontactget.databinding.ActivityAddContactBinding
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

        binding.btnAddContactSave.setOnClickListener {
            val sweetie = SweetieInfo(
                binding.ivAddContactImage.drawable,
                binding.etAddContactName.text.toString(),
                binding.etAddContactPhoneNumber.text.toString(),
                binding.etAddContactPhoneNumber2.text.toString(),
                binding.etAddContactPhoneNumber3.text.toString(),
                0,
                false
            )
            DataObject.addSweetieInfo(sweetie)
            finish()

            //저장 시 ContactFragment로 넘겨줄 데이터
//            var contact_fragment = ContactFragment()
//            var bundle = Bundle()
//            binding.apply {
//                bundle.apply {
//                    putString("name", etAddContactName.text.toString())
//                    putString("phone_number",etAddContactPhoneNumber.text.toString())
//                    putString("phone_number2",etAddContactPhoneNumber2.text.toString())
//                    putString("phone_number3",etAddContactPhoneNumber3.text.toString())
//                    putString("event_information",etAddContactEventInformation.text.toString())
//                    putString("relationship",etAddContactRelationship.text.toString())
//                    putString("group",etAddContactGroup.text.toString())
//                    putString("memo",etAddContactMemo.text.toString())
//                }
//            }
//            contact_fragment.arguments = bundle
//
//            supportFragmentManager!!.beginTransaction().replace(R.id.rv_all_contact_fragment, contact_fragment).commit()
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