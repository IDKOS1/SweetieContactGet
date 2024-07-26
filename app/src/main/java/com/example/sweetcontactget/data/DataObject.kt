package com.example.sweetcontactget.data

import android.content.Context
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.sweetcontactget.R
import com.example.sweetcontactget.util.KoreanMatcher.groupByIndex
import java.time.LocalDate

object DataObject {
    private val context: Context by lazy {
        ContactApplication.applicationContext()
    }

    fun getValidKeys():List<Int>{
        return contactMap.keys.toList()
    }
    fun getSweetieInfo(sweetieId: Int): SweetieInfo {
        return contactMap[sweetieId]!!
    }

    fun deleteSweetieInfo(sweetieId: Int) {
        contactMap.remove(sweetieId)
        contactData.clear()
        contactData.apply { addAll(groupByIndex(contactMap)) }
    }

    fun changedBookmark(sweetieId: Int, isMarked: Boolean) {
        contactMap[sweetieId]!!.isMarked = isMarked
    }

    fun isMarked(sweetieId: Int): Boolean {
        return contactMap[sweetieId]!!.isMarked
    }

    fun addSweetieInfo(sweetieInfo: SweetieInfo) {
        val newKey = contactMap.maxOf { it.key } + 1
        if (sweetieInfo.number in contactMap.values.map{ it.number }){
            Toast.makeText(context, "동일한 번호로 저장된 연락처가 있습니다.", Toast.LENGTH_SHORT).show()
        }else{
            contactMap[newKey] = sweetieInfo
        }

    }

    fun increaseHeart(sweetieId: Int, heart: Int) {
        contactMap[sweetieId]!!.heart += heart
    }

    fun editProfile(editTarget: String, content: String) {
        when (editTarget) {
            "이름" -> myProfileData.name = content
            "전화번호" -> myProfileData.phone_number = content
            "주소" -> myProfileData.address = content
            "소개" -> myProfileData.infoMessage = content
            "birthday" -> myProfileData.birthday = LocalDate.parse(content)
        }
    }

    fun editContact(sweetieId: Int, editTarget: String, content: String) {
        val contact = contactMap[sweetieId]
        if (contact != null) {
            when (editTarget) {
                "이름" -> contact.name = content
                "전화번호" -> contact.number = content
                "관계" -> contact.relationship = content
                "메모" -> contact.memo = content
            }
        }
    }


    private val contactMap: MutableMap<Int, SweetieInfo> = mutableMapOf(
        1 to SweetieInfo(
            imgSrc = ContextCompat.getDrawable(
                context,
                R.drawable.img_sweetie_1
            ),
            name = "감우",
            number = "01023453444",
            relationship = "친구",
            memo = "멍청하다.",
            heart = 0,
            isMarked = false
        ),
        2 to SweetieInfo(
            imgSrc = ContextCompat.getDrawable(
                context,
                R.drawable.img_sweetie_2
            ),
            name = "고세구",
            number = "01023453495",
            relationship = "친구",
            memo = "멍청하다.",
            heart = 20,
            isMarked = false
        ),
        3 to SweetieInfo(
            imgSrc = ContextCompat.getDrawable(
                context,
                R.drawable.img_sweetie_3
            ),
            name = "골드 쉽",
            number = "01098765432",
            relationship = "학교",
            memo = "착하다.",
            heart = 40,
            isMarked = false
        ),
        4 to SweetieInfo(
            imgSrc = ContextCompat.getDrawable(
                context,
                R.drawable.img_sweetie_4
            ),
            name = "그웬",
            number = "01087654321",
            relationship = "직장",
            memo = "성실하다.",
            heart = 60,
            isMarked = false
        ),
        5 to SweetieInfo(
            imgSrc = ContextCompat.getDrawable(
                context,
                R.drawable.img_sweetie_5
            ),
            name = "나고미 유이",
            number = "01076543210",
            relationship = "친구",
            memo = "밝다.",
            heart = 100,
            isMarked = false
        ),
        6 to SweetieInfo(
            imgSrc = ContextCompat.getDrawable(
                context,
                R.drawable.img_sweetie_6
            ),
            name = "니콜",
            number = "01065432109",
            relationship = "학교",
            memo = "조용하다.",
            heart = 0,
            isMarked = false
        ),
        7 to SweetieInfo(
            imgSrc = ContextCompat.getDrawable(
                context,
                R.drawable.img_sweetie_7
            ),
            name = "닐루",
            number = "01054321098",
            relationship = "직장",
            memo = "똑똑하다.",
            heart = 0,
            isMarked = false
        ),

        8 to SweetieInfo(
            imgSrc = ContextCompat.getDrawable(
                context,
                R.drawable.img_sweetie_8
            ),
            name = "라이덴",
            number = "01043210987",
            relationship = "친구",
            memo = "강하다.",
            heart = 0,
            isMarked = false
        ),
        9 to SweetieInfo(
            imgSrc = ContextCompat.getDrawable(
                context,
                R.drawable.img_sweetie_9
            ),
            name = "럭스",
            number = "01032109876",
            relationship = "학교",
            memo = "아름답다.",
            heart = 0,
            isMarked = false
        ),
        10 to SweetieInfo(
            imgSrc = ContextCompat.getDrawable(
                context,
                R.drawable.img_sweetie_10
            ),
            name = "릴리아",
            number = "01021098765",
            relationship = "직장",
            memo = "침착하다.",
            heart = 0,
            isMarked = false
        ),
        11 to SweetieInfo(
            imgSrc = ContextCompat.getDrawable(
                context,
                R.drawable.img_sweetie_11
            ),
            name = "릴파",
            number = "01010987654",
            relationship = "친구",
            memo = "재밌다.",
            heart = 0,
            isMarked = false
        ),
        12 to SweetieInfo(
            imgSrc = ContextCompat.getDrawable(
                context,
                R.drawable.img_sweetie_12
            ),
            name = "마키마",
            number = "01009876543",
            relationship = "직장",
            memo = "카리스마 있다.",
            heart = 0,
            isMarked = false
        ),
        13 to SweetieInfo(
            imgSrc = ContextCompat.getDrawable(
                context,
                R.drawable.img_sweetie_13
            ),
            name = "미스포츈",
            number = "01087654321",
            relationship = "친구",
            memo = "모험적이다.",
            heart = 0,
            isMarked = false
        ),
        14 to SweetieInfo(
            imgSrc = ContextCompat.getDrawable(
                context,
                R.drawable.img_sweetie_14
            ),
            name = "미츠리",
            number = "01076543210",
            relationship = "학교",
            memo = "애정이 많다.",
            heart = 0,
            isMarked = false
        ),
        15 to SweetieInfo(
            imgSrc = ContextCompat.getDrawable(
                context,
                R.drawable.img_sweetie_15
            ),
            name = "미카사",
            number = "01065432109",
            relationship = "직장",
            memo = "충성스럽다.",
            heart = 0,
            isMarked = false
        ),
        16 to SweetieInfo(
            imgSrc = ContextCompat.getDrawable(
                context,
                R.drawable.img_sweetie_16
            ),
            name = "바바라",
            number = "01054321098",
            relationship = "친구",
            memo = "활발하다.",
            heart = 0,
            isMarked = false
        ),
        17 to SweetieInfo(
            imgSrc = ContextCompat.getDrawable(
                context,
                R.drawable.img_sweetie_17
            ),
            name = "반디",
            number = "01043210987",
            relationship = "학교",
            memo = "상냥하다.",
            heart = 0,
            isMarked = false
        ),
        18 to SweetieInfo(
            imgSrc = ContextCompat.getDrawable(
                context,
                R.drawable.img_sweetie_18
            ),
            name = "방예나",
            number = "01032109876",
            relationship = "직장",
            memo = "효율적이다.",
            heart = 0,
            isMarked = false
        ),
        19 to SweetieInfo(
            imgSrc = ContextCompat.getDrawable(
                context,
                R.drawable.img_sweetie_19
            ),
            name = "산고노미야 코코미",
            number = "01021098765",
            relationship = "친구",
            memo = "친절하다.",
            heart = 0,
            isMarked = false
        ),
        20 to SweetieInfo(
            imgSrc = ContextCompat.getDrawable(
                context,
                R.drawable.img_sweetie_20
            ),
            name = "설지",
            number = "01010987654",
            relationship = "학교",
            memo = "명랑하다.",
            heart = 0,
            isMarked = false
        ),
        21 to SweetieInfo(
            imgSrc = ContextCompat.getDrawable(
                context,
                R.drawable.img_sweetie_21
            ),
            name = "세라핀",
            number = "01009876543",
            relationship = "직장",
            memo = "창의적이다.",
            heart = 0,
            isMarked = false
        ),
        22 to SweetieInfo(
            imgSrc = ContextCompat.getDrawable(
                context,
                R.drawable.img_sweetie_22
            ),
            name = "세일러문",
            number = "01087654321",
            relationship = "친구",
            memo = "용감하다.",
            heart = 0,
            isMarked = false
        ),
        23 to SweetieInfo(
            imgSrc = ContextCompat.getDrawable(
                context,
                R.drawable.img_sweetie_23
            ),
            name = "소나",
            number = "01076543210",
            relationship = "학교",
            memo = "조용하다.",
            heart = 0,
            isMarked = false
        ),
        24 to SweetieInfo(
            imgSrc = ContextCompat.getDrawable(
                context,
                R.drawable.img_sweetie_24
            ),
            name = "수희",
            number = "01065432109",
            relationship = "직장",
            memo = "열정적이다.",
            heart = 0,
            isMarked = false
        ),
        25 to SweetieInfo(
            imgSrc = ContextCompat.getDrawable(
                context,
                R.drawable.img_sweetie_25
            ),
            name = "스커크",
            number = "01054321098",
            relationship = "친구",
            memo = "똑똑하다.",
            heart = 0,
            isMarked = false
        ),
        26 to SweetieInfo(
            imgSrc = ContextCompat.getDrawable(
                context,
                R.drawable.img_sweetie_26
            ),
            name = "스파클",
            number = "01043210987",
            relationship = "학교",
            memo = "반짝이다.",
            heart = 0,
            isMarked = false
        ),
        27 to SweetieInfo(
            imgSrc = ContextCompat.getDrawable(
                context,
                R.drawable.img_sweetie_27
            ),
            name = "시노부",
            number = "01032109876",
            relationship = "직장",
            memo = "냉철하다.",
            heart = 0,
            isMarked = false
        ),
        28 to SweetieInfo(
            imgSrc = ContextCompat.getDrawable(
                context,
                R.drawable.img_sweetie_28
            ),
            name = "신학",
            number = "01021098765",
            relationship = "친구",
            memo = "진지하다.",
            heart = 0,
            isMarked = false
        ),
        29 to SweetieInfo(
            imgSrc = ContextCompat.getDrawable(
                context,
                R.drawable.img_sweetie_29
            ),
            name = "아델라",
            number = "01010987654",
            relationship = "학교",
            memo = "총명하다.",
            heart = 0,
            isMarked = false
        ),
        30 to SweetieInfo(
            imgSrc = ContextCompat.getDrawable(
                context,
                R.drawable.img_sweetie_30
            ),
            name = "아리",
            number = "01009876543",
            relationship = "직장",
            memo = "매력적이다.",
            heart = 0,
            isMarked = false
        ),
        31 to SweetieInfo(
            imgSrc = ContextCompat.getDrawable(
                context,
                R.drawable.img_sweetie_31
            ),
            name = "아메",
            number = "01087654321",
            relationship = "친구",
            memo = "쾌활하다.",
            heart = 0,
            isMarked = false
        ),
        32 to SweetieInfo(
            imgSrc = ContextCompat.getDrawable(
                context,
                R.drawable.img_sweetie_32
            ),
            name = "야에 미코",
            number = "01076543210",
            relationship = "학교",
            memo = "신비롭다.",
            heart = 0,
            isMarked = false
        ),
        33 to SweetieInfo(
            imgSrc = ContextCompat.getDrawable(
                context,
                R.drawable.img_sweetie_33
            ),
            name = "엘렌조",
            number = "01065432109",
            relationship = "직장",
            memo = "실용적이다.",
            heart = 0,
            isMarked = false
        ),
        34 to SweetieInfo(
            imgSrc = ContextCompat.getDrawable(
                context,
                R.drawable.img_sweetie_34
            ),
            name = "여르미",
            number = "01054321098",
            relationship = "친구",
            memo = "유머러스하다.",
            heart = 0,
            isMarked = false
        ),
        35 to SweetieInfo(
            imgSrc = ContextCompat.getDrawable(
                context,
                R.drawable.img_sweetie_35
            ),
            name = "완매",
            number = "01043210987",
            relationship = "학교",
            memo = "온화하다.",
            heart = 0,
            isMarked = false
        ),
        36 to SweetieInfo(
            imgSrc = ContextCompat.getDrawable(
                context,
                R.drawable.img_sweetie_36
            ),
            name = "유라",
            number = "01032109876",
            relationship = "직장",
            memo = "기품있다.",
            heart = 0,
            isMarked = false
        ),
        37 to SweetieInfo(
            imgSrc = ContextCompat.getDrawable(
                context,
                R.drawable.img_sweetie_37
            ),
            name = "음림",
            number = "01021098765",
            relationship = "친구",
            memo = "지혜롭다.",
            heart = 0,
            isMarked = false
        ),
        38 to SweetieInfo(
            imgSrc = ContextCompat.getDrawable(
                context,
                R.drawable.img_sweetie_38
            ),
            name = "이렐리아",
            number = "01010987654",
            relationship = "학교",
            memo = "강인하다.",
            heart = 0,
            isMarked = false
        ),
        39 to SweetieInfo(
            imgSrc = ContextCompat.getDrawable(
                context,
                R.drawable.img_sweetie_39
            ),
            name = "이오몽",
            number = "01009876543",
            relationship = "직장",
            memo = "기발하다.",
            heart = 0,
            isMarked = false
        ),
        40 to SweetieInfo(
            imgSrc = ContextCompat.getDrawable(
                context,
                R.drawable.img_sweetie_40
            ),
            name = "자야",
            number = "01087654321",
            relationship = "친구",
            memo = "자유롭다.",
            heart = 0,
            isMarked = false
        ),
        41 to SweetieInfo(
            imgSrc = ContextCompat.getDrawable(
                context,
                R.drawable.img_sweetie_41
            ),
            name = "잔나",
            number = "01076543210",
            relationship = "학교",
            memo = "산뜻하다.",
            heart = 0,
            isMarked = false
        ),
        42 to SweetieInfo(
            imgSrc = ContextCompat.getDrawable(
                context,
                R.drawable.img_sweetie_42
            ),
            name = "조이",
            number = "01065432109",
            relationship = "직장",
            memo = "명랑하다.",
            heart = 0,
            isMarked = false
        ),
        43 to SweetieInfo(
            imgSrc = ContextCompat.getDrawable(
                context,
                R.drawable.img_sweetie_43
            ),
            name = "징버거",
            number = "01054321098",
            relationship = "친구",
            memo = "다정하다.",
            heart = 0,
            isMarked = false
        ),
        44 to SweetieInfo(
            imgSrc = ContextCompat.getDrawable(
                context,
                R.drawable.img_sweetie_44
            ),
            name = "치오리",
            number = "01043210987",
            relationship = "학교",
            memo = "활기차다.",
            heart = 0,
            isMarked = false
        ),
        45 to SweetieInfo(
            imgSrc = ContextCompat.getDrawable(
                context,
                R.drawable.img_sweetie_45
            ),
            name = "카프카",
            number = "01032109876",
            relationship = "직장",
            memo = "분석적이다.",
            heart = 0,
            isMarked = false
        ),
        46 to SweetieInfo(
            imgSrc = ContextCompat.getDrawable(
                context,
                R.drawable.img_sweetie_46
            ),
            name = "케이틀린",
            number = "01021098765",
            relationship = "친구",
            memo = "정확하다.",
            heart = 0,
            isMarked = false
        ),
        47 to SweetieInfo(
            imgSrc = ContextCompat.getDrawable(
                context,
                R.drawable.img_sweetie_47
            ),
            name = "키타가미 미미",
            number = "01010987654",
            relationship = "학교",
            memo = "냉철하다.",
            heart = 0,
            isMarked = false
        ),
        48 to SweetieInfo(
            imgSrc = ContextCompat.getDrawable(
                context,
                R.drawable.img_sweetie_48
            ),
            name = "키타가와 마린",
            number = "01009876543",
            relationship = "직장",
            memo = "열정적이다.",
            heart = 0,
            isMarked = false
        ),
        49 to SweetieInfo(
            imgSrc = ContextCompat.getDrawable(
                context,
                R.drawable.img_sweetie_49
            ),
            name = "토파즈",
            number = "01087654321",
            relationship = "친구",
            memo = "반짝이다.",
            heart = 0,
            isMarked = false
        ),
        50 to SweetieInfo(
            imgSrc = ContextCompat.getDrawable(
                context,
                R.drawable.img_sweetie_50
            ),
            name = "푸리나",
            number = "01076543210",
            relationship = "학교",
            memo = "창의적이다.",
            heart = 0,
            isMarked = false
        ),
        51 to SweetieInfo(
            imgSrc = ContextCompat.getDrawable(
                context,
                R.drawable.img_sweetie_51
            ),
            name = "피슬",
            number = "01065432109",
            relationship = "직장",
            memo = "매력적이다.",
            heart = 0,
            isMarked = false
        ),
        52 to SweetieInfo(
            imgSrc = ContextCompat.getDrawable(
                context,
                R.drawable.img_sweetie_52
            ),
            name = "하나코나나",
            number = "01054321098",
            relationship = "친구",
            memo = "유쾌하다.",
            heart = 0,
            isMarked = false
        ),
        53 to SweetieInfo(
            imgSrc = ContextCompat.getDrawable(
                context,
                R.drawable.img_sweetie_53
            ),
            name = "한결",
            number = "01043210987",
            relationship = "학교",
            memo = "의젓하다.",
            heart = 0,
            isMarked = false
        ),
        54 to SweetieInfo(
            imgSrc = ContextCompat.getDrawable(
                context,
                R.drawable.img_sweetie_54
            ),
            name = "호시노 아이",
            number = "01032109876",
            relationship = "직장",
            memo = "빛나다.",
            heart = 0,
            isMarked = false
        ),
        55 to SweetieInfo(
            imgSrc = ContextCompat.getDrawable(
                context,
                R.drawable.img_sweetie_55
            ),
            name = "후부키",
            number = "01021098765",
            relationship = "친구",
            memo = "쿨하다.",
            heart = 0,
            isMarked = false
        ),
        56 to SweetieInfo(
            imgSrc = ContextCompat.getDrawable(
                context,
                R.drawable.img_sweetie_56
            ),
            name = "u32",
            number = "01010987654",
            relationship = "학교",
            memo = "미지수이다.",
            heart = 0,
            isMarked = false
        )
    )

    val myProfileData: MyProfile = MyProfile(
        R.drawable.img_kangjin,
        "이강진",
        "01078984555",
        "경기도 오산시 너구리구",
        LocalDate.of(1999, 5, 11),
        "미녀들 다 내꺼~"
    )

    val randomCallList = mutableListOf<Contact>()

    val contactData: MutableList<Contact> = mutableListOf()
    val contactList get() = groupByIndex(contactMap.sortedByName())
    val bookmarkData get() = contactMap.filter { it.value.isMarked }

    init {
        contactData.apply { addAll(groupByIndex(contactMap)) }
    }

    private fun MutableMap<Int, SweetieInfo>.sortedByName() =
        this.toList().sortedBy { it.second.name }.toMap().toMutableMap()
}






