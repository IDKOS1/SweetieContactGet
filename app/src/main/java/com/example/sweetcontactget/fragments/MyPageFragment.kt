package com.example.sweetcontactget.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.example.sweetcontactget.R
import com.example.sweetcontactget.data.DataObject
import com.example.sweetcontactget.databinding.FragmentMyPageBinding
import com.example.sweetcontactget.dialog.EditTextDialog
import com.example.sweetcontactget.dialog.RandomCallDialog


class MyPageFragment : Fragment() {
    private lateinit var binding: FragmentMyPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyPageBinding.inflate(layoutInflater)
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


            //Dialog
            //이름 클릭 시 다이얼로그 열림
            tvMypageName.setOnClickListener {
                val dialog = EditTextDialog(requireContext())
                dialog.show("${resources.getString(R.string.common_name)} 편집",resources.getString(R.string.common_name))

                //다이얼로그 저장 버튼을 눌렀을 때 text 변경
                dialog.setOnClickedListener(object : EditTextDialog.ButtonClickListener {
                    override fun onClicked(myName: String) {
                        tvMypageName.text = myName
                    }
                })
            }

            tvMypageNumber.setOnClickListener {
                val dialog = EditTextDialog(requireContext())
                dialog.show("${resources.getString(R.string.common_phone_number)} 편집",resources.getString(R.string.common_phone_number))

                //다이얼로그 저장 버튼을 눌렀을 때 text 변경
                dialog.setOnClickedListener(object : EditTextDialog.ButtonClickListener {
                    override fun onClicked(myName: String) {
                        tvMypageNumber.text = myName
                    }
                })
            }

            tvMypageBirthday.setOnClickListener {
                val dialog = EditTextDialog(requireContext())
                dialog.show("${resources.getString(R.string.mypage_birthday)} 편집",resources.getString(R.string.mypage_birthday))

                //다이얼로그 저장 버튼을 눌렀을 때 text 변경
                dialog.setOnClickedListener(object : EditTextDialog.ButtonClickListener {
                    override fun onClicked(myName: String) {
                        tvMypageBirthday.text = myName
                    }
                })
            }

            tvMypageAddres.setOnClickListener {
                val dialog = EditTextDialog(requireContext())
                dialog.show("${resources.getString(R.string.mypage_address)} 편집",resources.getString(R.string.mypage_address))

                //다이얼로그 저장 버튼을 눌렀을 때 text 변경
                dialog.setOnClickedListener(object : EditTextDialog.ButtonClickListener {
                    override fun onClicked(myName: String) {
                        tvMypageAddres.text = myName
                    }
                })
            }

            tvMypageInfoMessage.setOnClickListener {
                val dialog = EditTextDialog(requireContext())
                dialog.show("${resources.getString(R.string.mypage_introduce)} 편집",resources.getString(R.string.mypage_introduce))

                //다이얼로그 저장 버튼을 눌렀을 때 text 변경
                dialog.setOnClickedListener(object : EditTextDialog.ButtonClickListener {
                    override fun onClicked(myName: String) {
                        tvMypageInfoMessage.text = myName
                    }
                })
            }
            //
        }

        return binding.root
    }
}

