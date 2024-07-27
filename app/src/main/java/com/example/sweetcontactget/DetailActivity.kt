package com.example.sweetcontactget

import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.example.sweetcontactget.data.DataObject
import com.example.sweetcontactget.data.SweetieInfo
import com.example.sweetcontactget.databinding.ActivityDetailBinding
import com.example.sweetcontactget.dialog.EditTextDialog
import com.example.sweetcontactget.util.Util

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private var sweetieId: Int = -1
    private var sweetie: SweetieInfo? = null

    private var pickImageUri: Uri? = null


    private val cropImage = registerForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {

            pickImageUri = result.uriContent
            binding.ivDetailProfile.setImageURI(pickImageUri)

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



        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sweetieId = intent.getIntExtra("sweetieId", -1)
        if (sweetieId != -1) {
            sweetie = DataObject.getSweetieInfo(sweetieId)
        }

        requestCallPermission()

        updateDetail()


        binding.run {

            ivDetailProfile.setOnClickListener {
                pickImageLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }

            tvDetailName.setOnClickListener {
                editContent("name","이름")
            }

            llDetailNumber.setOnClickListener {
                editContent("number","전화번호")
            }

            llDetailNumber2.setOnClickListener {
                editContent("number","전화번호2")
            }

            llDetailNumber3.setOnClickListener {
                editContent("number","전화번호3")
            }

            tvDetailAddNumber.setOnClickListener {
                if (llDetailNumber2.visibility == View.GONE && llDetailNumber3.visibility == View.GONE){
                    editContent("number","전화번호2")
                }else if(llDetailNumber2.visibility == View.VISIBLE && llDetailNumber3.visibility ==View.GONE){
                    editContent("number","전화번호3")
                }else{
                    Toast.makeText(this@DetailActivity, "전화번호는 3개 까지만 저장할 수 있습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            llDetailRelationship.setOnClickListener {
                editContent("allText","관계")
            }

            llDetailMemo.setOnClickListener {
                editContent("allText","메모")
            }


            sweetie?.let { sweetie ->
                tvDetailMessage.setOnClickListener {
                    Util.sendMessage(this@DetailActivity, sweetie.number)
                }

                tvDetailCall.setOnClickListener {
                    Util.callSweetie(this@DetailActivity, sweetie.number)
                }
            }

            ivDetailBack.setOnClickListener {
                finish()
            }

            tbDetailMark.setOnClickListener {
                val isMarked = !DataObject.isMarked(sweetieId)
                DataObject.changedBookmark(sweetieId, isMarked)
                val toastMessage = if (isMarked) "즐겨찾기 추가." else "즐겨찾기 삭제."
                Util.showToast(this@DetailActivity, toastMessage)
            }

            ivDetailDelete.setOnClickListener {
                // TODO: 삭제 확인 다이얼로그 생성
                if (sweetieId != -1) {
                    DataObject.deleteSweetieInfo(sweetieId)
                    finish()
                    Util.showToast(this@DetailActivity, "삭제되었습니다.")
                } else {
                    Util.showToast(this@DetailActivity, "삭제할 대상이 없습니다.")
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        updateDetail()
    }

    private fun editContent(type: String, editTarget: String) {
        val dialog = EditTextDialog(this)
        dialog.show(type,"$editTarget 편집", editTarget)

        //다이얼로그 저장 버튼을 눌렀을 때 text 변경
        dialog.setOnClickedListener(object : EditTextDialog.ButtonClickListener {
            override fun onClicked(content: String) {
                DataObject.editContact(sweetieId,editTarget, content)
                updateDetail()
                Util.showToast(this@DetailActivity, "수정 되었습니다.")
            }
        })
    }

    private fun updateDetail() {
        sweetie?.let { sweetie ->
            binding.run {
                ivDetailProfile.setImageDrawable(sweetie.imgSrc)
                tvDetailName.text = sweetie.name
                tvDetailNumber.text = sweetie.number
                tvDetailNumber2.text = sweetie.secondNumber
                tvDetailNumber3.text =sweetie.thirdNumber
                rbHeartRating.rating = sweetie.heart / 20.toFloat()
                tvDetailRelationship.text = sweetie.relationship
                tvDetailMemo.text = sweetie.memo
                tbDetailMark.isChecked = sweetie.isMarked

                llDetailNumber2.visibility = if (sweetie.secondNumber.isNullOrEmpty()) View.GONE else View.VISIBLE
                llDetailNumber3.visibility = if (sweetie.thirdNumber.isNullOrEmpty()) View.GONE else View.VISIBLE
            }
        }
    }

    private fun requestCallPermission() {
        val status = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE)
        if (status != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.CALL_PHONE),
                10
            )
        }
    }
}
