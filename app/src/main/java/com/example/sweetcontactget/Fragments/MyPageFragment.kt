package com.example.sweetcontactget.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sweetcontactget.Data.DataObject
import com.example.sweetcontactget.Util.Util
import com.example.sweetcontactget.databinding.FragmentMyPageBinding


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
        }

        return binding.root
    }
}

