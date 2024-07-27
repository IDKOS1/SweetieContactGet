package com.example.sweetcontactget

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
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
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.example.sweetcontactget.data.DataObject
import com.example.sweetcontactget.data.SweetieInfo
import com.example.sweetcontactget.data.formatPhoneNumber
import com.example.sweetcontactget.data.isRegularEvent
import com.example.sweetcontactget.data.isRegularName
import com.example.sweetcontactget.data.isRegularPhoneNumber
import com.example.sweetcontactget.data.isRegularRelationShip
import com.example.sweetcontactget.databinding.ActivityAddContactBinding
import com.example.sweetcontactget.util.Util.initSpinner
import kotlin.random.Random
import kotlin.random.nextInt

class AddContactActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddContactBinding
    private var count = 0

    private var pickImageUri: Uri? = null


    private val cropImage = registerForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {

            pickImageUri = result.uriContent
            binding.ivAddContactImage.setImageURI(pickImageUri)

        } else {
            val exception = result.error
        }
    }


    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                cropImage.launch(
                    CropImageContractOptions(
                        uri = uri, // 크롭할 이미지 uri
                        cropImageOptions = CropImageOptions(
                            outputCompressFormat = Bitmap.CompressFormat.PNG,//사진 확장자 변경
                            minCropResultHeight = 50,//사진 최소 세로크기
                            minCropResultWidth = 50,//사진 최소 가로크기
                            aspectRatioY = 5,//세로 비율
                            aspectRatioX = 8,//가로 비율
                            fixAspectRatio = false,//커터? 크기 고정 여부
                            borderLineColor = Color.MAGENTA//커터? 태두리 색
                            // 원하는 옵션 추가
                        )
                    )
                )
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        var isName = false
        var isPhoneNumber = false
        var isPhoneNumber2 = true
        var isPhoneNumber3 = true
        var isEvent = false
        var isRelationShip = false

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

        initSpinner(this@AddContactActivity, binding.groupSpinner, null)

        //갤러리 이미지 가져와서 추가
        binding.ivAddContactImage.setOnClickListener {
            pickImageLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }


        //이름 유효성 검사
        binding.etAddContactName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                when {
                    isRegularName(binding.etAddContactName.text.toString().trim()) -> {
                        binding.ivCheckOkayName.visibility = View.VISIBLE
                        binding.tvAddContactWrongName.visibility = View.INVISIBLE
                        isName = true
                    }

                    binding.etAddContactName.text.toString().trim().isEmpty() -> {
                        binding.tvAddContactWrongName.text =
                            resources.getString(R.string.add_contact_empty_name)
                        binding.ivCheckOkayName.visibility = View.INVISIBLE
                        binding.tvAddContactWrongName.visibility = View.VISIBLE
                        isName = false
                    }

                    else -> {
                        binding.tvAddContactWrongName.text =
                            resources.getString(R.string.add_contact_placeholder_wrong_name)
                        binding.ivCheckOkayName.visibility = View.INVISIBLE
                        binding.tvAddContactWrongName.visibility = View.VISIBLE
                        isName = false
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        //전화번호 유효성 검사1
        binding.etAddContactPhoneNumber.addTextChangedListener(object : TextWatcher {
            private var isFormat: Boolean = false
            private var isDeleteHyphen: Boolean = false

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if (count > 0 && s?.get(start) == '-') {
                    isDeleteHyphen = true
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                when {
                    isRegularPhoneNumber(
                        binding.etAddContactPhoneNumber.text.toString().trim()
                    ) -> {
                        binding.ivCheckOkayPhoneNumber.visibility = View.VISIBLE
                        binding.tvAddContactWrongNumber.visibility = View.INVISIBLE
                        isPhoneNumber = true
                    }

                    binding.etAddContactPhoneNumber.text.toString().trim().isEmpty() -> {
                        binding.tvAddContactWrongNumber.text =
                            resources.getString(R.string.add_contact_empty_phone_number)
                        binding.ivCheckOkayPhoneNumber.visibility = View.INVISIBLE
                        binding.tvAddContactWrongNumber.visibility = View.VISIBLE
                        isPhoneNumber = false
                    }

                    else -> {
                        binding.tvAddContactWrongNumber.text =
                            resources.getString(R.string.add_contact_placeholder_wrong_phone_number)
                        binding.ivCheckOkayPhoneNumber.visibility = View.INVISIBLE
                        binding.tvAddContactWrongNumber.visibility = View.VISIBLE
                        isPhoneNumber = false
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {
                if (isFormat || isDeleteHyphen) return
                isFormat = true

                val text = s.toString().replace("-", "")
                val formattedNumber = formatPhoneNumber(text)

                s?.replace(0, s.length, formattedNumber)
                isFormat = false
            }

        })

        //전화번호 유효성 검사2
        binding.etAddContactPhoneNumber2.addTextChangedListener(object : TextWatcher {

            private var isFormat: Boolean = false
            private var isDeleteHyphen: Boolean = false

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if (count > 0 && s?.get(start) == '-') {
                    isDeleteHyphen = true
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                when {
                    isRegularPhoneNumber(
                        binding.etAddContactPhoneNumber2.text.toString().trim()
                    ) -> {
                        binding.ivCheckOkayPhoneNumber2.visibility = View.VISIBLE
                        binding.tvAddContactWrongNumber2.visibility = View.INVISIBLE
                        isPhoneNumber2 = true
                    }

                    binding.etAddContactPhoneNumber2.text.toString().trim().isEmpty() -> {
                        binding.tvAddContactWrongNumber2.text =
                            resources.getString(R.string.add_contact_empty_phone_number2)
                        binding.ivCheckOkayPhoneNumber2.visibility = View.INVISIBLE
                        binding.tvAddContactWrongNumber2.visibility = View.VISIBLE
                        isPhoneNumber2 = true
                    }

                    else -> {
                        binding.tvAddContactWrongNumber2.text =
                            resources.getString(R.string.add_contact_placeholder_wrong_phone_number)
                        binding.ivCheckOkayPhoneNumber2.visibility = View.INVISIBLE
                        binding.tvAddContactWrongNumber2.visibility = View.VISIBLE
                        isPhoneNumber2 = false
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {
                if (isFormat || isDeleteHyphen) return
                isFormat = true

                val text = s.toString().replace("-", "")
                val formattedNumber = formatPhoneNumber(text)

                s?.replace(0, s.length, formattedNumber)
                isFormat = false
            }
        })

        //전화번호 유효성 검사3
        binding.etAddContactPhoneNumber3.addTextChangedListener(object : TextWatcher {
            private var isFormat: Boolean = false
            private var isDeleteHyphen: Boolean = false

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if (count > 0 && s?.get(start) == '-') {
                    isDeleteHyphen = true
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                when {
                    isRegularPhoneNumber(
                        binding.etAddContactPhoneNumber3.text.toString().trim()
                    ) -> {
                        binding.ivCheckOkayPhoneNumber3.visibility = View.VISIBLE
                        binding.tvAddContactWrongNumber3.visibility = View.INVISIBLE
                        isPhoneNumber3 = true
                    }

                    binding.etAddContactPhoneNumber3.text.toString().trim().isEmpty() -> {
                        binding.tvAddContactWrongNumber3.text =
                            resources.getString(R.string.add_contact_empty_phone_number3)
                        binding.ivCheckOkayPhoneNumber3.visibility = View.INVISIBLE
                        binding.tvAddContactWrongNumber3.visibility = View.VISIBLE
                        isPhoneNumber3 = true
                    }

                    else -> {
                        binding.tvAddContactWrongNumber3.text =
                            resources.getString(R.string.add_contact_placeholder_wrong_phone_number)
                        binding.ivCheckOkayPhoneNumber3.visibility = View.INVISIBLE
                        binding.tvAddContactWrongNumber3.visibility = View.VISIBLE
                        isPhoneNumber3 = false
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {
                if (isFormat || isDeleteHyphen) return
                isFormat = true

                val text = s.toString().replace("-", "")
                val formattedNumber = formatPhoneNumber(text)

                s?.replace(0, s.length, formattedNumber)
                isFormat = false
            }
        })

        //이벤트 유효성 검사
        binding.etAddContactEventInformation.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                when {
                    isRegularEvent(binding.etAddContactEventInformation.text.toString().trim()) -> {
                        binding.ivCheckOkayEventInformation.visibility = View.VISIBLE
                        binding.tvAddContactWrongEventInformation.visibility = View.INVISIBLE
                        isEvent = true
                    }

                    binding.etAddContactEventInformation.text.toString().trim().isEmpty() -> {
                        binding.tvAddContactWrongEventInformation.text =
                            resources.getString(R.string.add_contact_empty_event_information)
                        binding.ivCheckOkayEventInformation.visibility = View.INVISIBLE
                        binding.tvAddContactWrongEventInformation.visibility = View.VISIBLE
                        isEvent = false
                    }

                    else -> {
                        binding.tvAddContactWrongEventInformation.text =
                            resources.getString(R.string.add_contact_placeholder_wrong_event_information)
                        binding.tvAddContactWrongEventInformation.visibility = View.VISIBLE
                        isEvent = false
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })


        binding.btnAddContactSave.setOnClickListener {
            val name = binding.etAddContactName.text.toString().trim()
            val phoneNumber = binding.etAddContactPhoneNumber.text.toString().trim()
            val eventInformation = binding.etAddContactEventInformation.text.toString().trim()
            val image = binding.ivAddContactImage.drawable

            // 비었을 때
            if (name.isEmpty() || phoneNumber.isEmpty() || eventInformation.isEmpty()
                ||  image == null
            ) {
                Toast.makeText(
                    this,
                    resources.getString(R.string.add_contact_empty_data),
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            //유효한 입력 체크
            if (!isName || !isPhoneNumber || !isPhoneNumber2 || !isPhoneNumber3 || !isEvent || !isRelationShip) {
                Toast.makeText(this, resources.getString(R.string.add_contact_wrong_data), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val sweetieInfo = SweetieInfo(
                imgSrc = binding.ivAddContactImage.drawable,
                name = binding.etAddContactName.text.toString(),
                number = binding.etAddContactPhoneNumber.text.toString(),
                relationship = binding.groupSpinner.selectedItemPosition,
                secondNumber = binding.etAddContactPhoneNumber2.text.toString(),
                thirdNumber = binding.etAddContactPhoneNumber3.text.toString(),
                memo = binding.etAddContactMemo.text.toString(),
                heart = 0,
                isMarked = false
            )

            if(DataObject.booleanSweetieInfo(sweetieInfo)){
                Toast.makeText(this, "동일한 번호로 저장된 연락처가 있습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else {
                DataObject.addSweetieInfo(sweetieInfo)
                finish()
            }

        }
        binding.btnAddContactCancel.setOnClickListener {
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }


}