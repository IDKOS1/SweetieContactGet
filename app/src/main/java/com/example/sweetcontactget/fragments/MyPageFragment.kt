package com.example.sweetcontactget.fragments

import CustomDatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sweetcontactget.data.DataObject
import com.example.sweetcontactget.databinding.FragmentMyPageBinding
import com.example.sweetcontactget.dialog.EditTextDialog
import com.example.sweetcontactget.util.Util


class MyPageFragment : Fragment() {
    private var _binding: FragmentMyPageBinding? = null
    private val binding get() = _binding!!
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

            tvMypageName.setOnClickListener {
                editContent("이름")
            }

            llMypageNumber.setOnClickListener {
                editContent("전화번호")
            }

            llMypageBirthday.setOnClickListener {
                val datePickerFragment = CustomDatePickerDialog {selectedYear, selectedMonth, selectedDay ->
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

                datePickerFragment.show(parentFragmentManager, "datePicker")
            }

            llMypageAddress.setOnClickListener {
                editContent("주소")
            }

            llMypageInfoMessage.setOnClickListener {
                editContent("소개")
            }
        }
        return binding.root
    }

    private fun editContent(editTarget: String) {
        val dialog = EditTextDialog(requireContext())
        dialog.show("$editTarget 편집", editTarget)

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
                userData.phone_number.replace(Regex("(\\d{3})(\\d{4})(\\d{4})"), "$1-$2-$3")
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

