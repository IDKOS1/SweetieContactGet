package com.example.sweetcontactget

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.sweetcontactget.data.DataObject
import com.example.sweetcontactget.databinding.ActivityDetailBinding
import com.example.sweetcontactget.util.Util

class DetailActivity : AppCompatActivity() {
    private val sweetieId by lazy { intent.getIntExtra("sweetieId", -1) }
    private val binding by lazy { ActivityDetailBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        val currentSweetieInfo = if (sweetieId != -1) DataObject.getSweetieInfo(sweetieId) else null

        binding.run {
            currentSweetieInfo?.let { sweetie ->
                ivDetailProfile.setImageDrawable(sweetie.imgSrc)
                tvDetailName.text = sweetie.name
                tvDetailNumber.text = sweetie.number
                rbHeartRating.rating = sweetie.heart / 20.toFloat()
                tvDetailRelationship.text = sweetie.relationship
                tvDetailMemo.text = sweetie.memo
                tbDetailMark.isChecked = sweetie.isMarked
                tvDetailMessage.setOnClickListener {
                    sweetie.number.let { number ->
                        Util.sendMessage(this@DetailActivity, number)
                    }
                }

                tvDetailCall.setOnClickListener {
                    sweetie.number.let { number ->
                        Util.callSweetie(this@DetailActivity, number)
                    }
                }
            }

            ivDetailBack.setOnClickListener {
                finish()
            }

            tbDetailMark.setOnClickListener {
                val isMarked = !DataObject.isMarked(sweetieId)
                DataObject.changedBookmark(sweetieId, isMarked)
                if(isMarked){
                    Util.showToast(this@DetailActivity, "즐겨찾기 추가.")
                } else {
                    Util.showToast(this@DetailActivity, "즐겨찾기 삭제.")
                }
            }

            ivDetailDelete.setOnClickListener {
                //TODO 삭제 확인 다이얼로그 생성
                if(sweetieId != -1){
                    DataObject.deleteSweetieInfo(sweetieId)
                    finish()
                    Util.showToast(this@DetailActivity, "삭제되었습니다.")
                } else {
                    Util.showToast(this@DetailActivity, "삭제할 대상이 없습니다..")
                }
            }

        }
    }
}
