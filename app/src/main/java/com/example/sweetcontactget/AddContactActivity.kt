package com.example.sweetcontactget

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.sweetcontactget.data.DataObject
import com.example.sweetcontactget.data.SweetieInfo
import com.example.sweetcontactget.data.isRegularEvent
import com.example.sweetcontactget.data.isRegularMemo
import com.example.sweetcontactget.data.isRegularName
import com.example.sweetcontactget.data.isRegularPhoneNumber
import com.example.sweetcontactget.data.isRegularRelationShip
import com.example.sweetcontactget.databinding.ActivityAddContactBinding

class AddContactActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddContactBinding
    private var count = 0

    private  lateinit var profileImage : ImageView
    private var pickImageUri : Uri? = null


    private val requestPermissonLauncher : ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()){isGranted ->
            if(isGranted){
                openGallery()
            }else{
                Toast.makeText(this,"갤러리 접근 권한이 필요합니다",Toast.LENGTH_SHORT).show()
            }
        }

    private val pickImageLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result ->
            if(result.resultCode == Activity.RESULT_OK){
                val data: Intent? = result.data
                data?.data?.let {
                    pickImageUri = it
                    profileImage.setImageURI(pickImageUri)
                }
            }

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        var isName = false
        var isPhoneNumber = false
        var isPhoneNumber2 = false
        var isPhoneNumber3 = false
        var isEvent = false
        var isRelationShip = false
        var isMemo = false

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

        //갤러리 이미지 가져와서 추가
        binding.ivAddContactImage.setOnClickListener {
            //랜덤이미지 추가하는 로직
//            val random = Random.nextInt(1..56)
//            val currentId = random.let { DataObject.getSweetieInfo(it) }
//
//            currentId.let {
//                binding.ivAddContactImage.setImageDrawable(it.imgSrc)
//            }
            profileImage = binding.ivAddContactImage

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.READ_MEDIA_IMAGES
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    openGallery()
                } else {
                    requestPermissonLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                }
            } else {
                if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    openGallery()
                } else {
                    requestPermissonLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            }
        }



        //이름 유효성 검사
        binding.etAddContactName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (isRegularName(binding.etAddContactName.text.toString().trim())) {
                    binding.tvAddContactWrongName.visibility = View.INVISIBLE
                    isName = true
                } else {
                    binding.tvAddContactWrongName.visibility = View.VISIBLE
                    isName = false
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        //전화번호 유효성 검사1
        binding.etAddContactPhoneNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (isRegularPhoneNumber(binding.etAddContactPhoneNumber.text.toString().trim())) {
                    binding.tvAddContactWrongNumber.visibility = View.INVISIBLE
                    isPhoneNumber = true
                } else {
                    binding.tvAddContactWrongNumber.visibility = View.VISIBLE
                    isPhoneNumber = false
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        //전화번호 유효성 검사2
        binding.etAddContactPhoneNumber2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (isRegularPhoneNumber(binding.etAddContactPhoneNumber2.text.toString().trim())) {
                    binding.tvAddContactWrongNumber2.visibility = View.INVISIBLE
                    isPhoneNumber2 = true
                } else {
                    binding.tvAddContactWrongNumber2.visibility = View.VISIBLE
                    isPhoneNumber2 = false
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        //전화번호 유효성 검사3
        binding.etAddContactPhoneNumber3.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (isRegularPhoneNumber(binding.etAddContactPhoneNumber3.text.toString().trim())) {
                    binding.tvAddContactWrongNumber3.visibility = View.INVISIBLE
                    isPhoneNumber3 = true
                } else {
                    binding.tvAddContactWrongNumber3.visibility = View.VISIBLE
                    isPhoneNumber3 = false
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        //이벤트 유효성 검사
        binding.etAddContactEventInformation.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (isRegularEvent(binding.etAddContactEventInformation.text.toString().trim())) {
                    binding.tvAddContactWrongEventInformation.visibility = View.INVISIBLE
                    isEvent = true
                } else {
                    binding.tvAddContactWrongEventInformation.visibility = View.VISIBLE
                    isEvent = false
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        //관계 유효성 검사
        binding.etAddContactRelationship.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (isRegularRelationShip(binding.etAddContactRelationship.text.toString().trim())) {
                    binding.tvAddContactWrongRelationship.visibility = View.INVISIBLE
                    isRelationShip = true
                } else {
                    binding.tvAddContactWrongRelationship.visibility = View.VISIBLE
                    isRelationShip = false
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        //메모 유효성 검사
        binding.etAddContactMemo.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (isRegularMemo(binding.etAddContactMemo.text.toString().trim())) {
                    binding.tvAddContactWrongMemo.visibility = View.INVISIBLE
                    isMemo = true
                } else {
                    binding.tvAddContactWrongMemo.visibility = View.VISIBLE
                    isMemo = false
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.btnAddContactSave.setOnClickListener {
            val name = binding.etAddContactName.text.toString().trim()
            val phoneNumber = binding.etAddContactPhoneNumber.text.toString().trim()
            val eventInformation = binding.etAddContactEventInformation.text.toString().trim()
            val relationShip = binding.etAddContactRelationship.text.toString().trim()
            val memo = binding.etAddContactMemo.text.toString().trim()
            val image = binding.ivAddContactImage.drawable

            // 비었을 때
            if (name.isEmpty() || phoneNumber.isEmpty() || eventInformation.isEmpty()
                || relationShip.isEmpty() || memo.isEmpty() || image == null
            ) {
                Toast.makeText(this,"입력하지 않은 정보가 있습니다.",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //유효한 입력 체크
            if (!isName || !isPhoneNumber || !isEvent || !isRelationShip || !isMemo) {
                Toast.makeText(this,"유효하지 않은 정보가 있습니다.",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            var sweetieInfo = SweetieInfo(
                imgSrc = binding.ivAddContactImage.drawable,
                name = binding.etAddContactName.text.toString(),
                number = binding.etAddContactPhoneNumber.text.toString(),
                relationship = binding.etAddContactRelationship.text.toString(),
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

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun openGallery(){
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        pickImageLauncher.launch(galleryIntent)
    }
}