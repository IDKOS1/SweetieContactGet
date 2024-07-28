package com.example.sweetcontactget.fragments

import CustomDatePickerDialog
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.example.sweetcontactget.data.DataObject
import com.example.sweetcontactget.databinding.FragmentMyPageBinding
import com.example.sweetcontactget.dialog.EditTextDialog
import com.example.sweetcontactget.util.Util


class MyPageFragment : Fragment() {
    private var _binding: FragmentMyPageBinding? = null
    private val binding get() = _binding!!

    private var pickImageUri: Uri? = null


    private val cropImage = registerForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            pickImageUri = result.uriContent
            binding.ivMypageProfile.setImageURI(pickImageUri)
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
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyPageBinding.inflate(layoutInflater)

        binding.run {
            updateMyInfo()

            ivMypageProfile.setOnClickListener {
                pickImageLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }

            tvMypageName.setOnClickListener {
                editContent("name","이름")
            }

            llMypageNumber.setOnClickListener {
                editContent("number","전화번호")
            }

            llMypageBirthday.setOnClickListener {
                val datePickerFragment = CustomDatePickerDialog(requireContext()) {selectedYear, selectedMonth, selectedDay ->
                    // 선택된 날짜를 처리
                    val selectedDate = String.format(
                        "%04d-%02d-%02d",
                        selectedYear,
                        selectedMonth + 1,
                        selectedDay
                    )
                    // 데이터 저장 (예: ViewModel 또는 데이터 객체에 저장)
                    DataObject.editProfile("birthday", selectedDate)

                    // 사용자에게 알림
                    updateMyInfo()
                    Util.showToast(requireContext(), "생년월일이 수정되었습니다.")
                }

                datePickerFragment.show()
            }

            llMypageAddress.setOnClickListener {
                editContent("allText","주소")
            }

            llMypageInfoMessage.setOnClickListener {
                editContent("allText","소개")
            }
        }
        return binding.root
    }

    private fun editContent(type : String,editTarget: String) {
        val dialog = EditTextDialog(requireContext())
        dialog.show(type,"$editTarget 편집", editTarget)

        //다이얼로그 저장 버튼을 눌렀을 때 text 변경
        dialog.setOnClickedListener(object : EditTextDialog.ButtonClickListener {
            override fun onClicked(content: String) {
                DataObject.editProfile(editTarget, content)
                updateMyInfo()
                Util.showToast(requireContext(), "수정 되었습니다.")
            }
        })
    }

    private fun updateMyInfo() {
        val userData = DataObject.myProfileData
        val birthday = userData.birthday

        binding.run {
            ivMypageProfile.setImageResource(userData.imgSrc)
            tvMypageName.text = userData.name
            tvMypageNumber.text =
                userData.phoneNumber.replace(Regex("(\\d{3})(\\d{4})(\\d{4})"), "$1-$2-$3")
            tvMypageBirthday.text =
                "${birthday.year}년 ${birthday.monthValue}월 ${birthday.dayOfMonth}일"
            tvMypageAddres.text = userData.address
            tvMypageInfoMessage.text = userData.infoMessage
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

