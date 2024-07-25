package com.example.sweetcontactget.util

import com.example.sweetcontactget.data.Contact

object KoreanMatcher {
    private const val KOREAN_UNICODE_START = 44032 // 가
    private const val KOREAN_UNICODE_END = 55203   // 힣
    private const val KOREAN_UNICODE_BASED = 588   // 각 자음 마다 가지는 글자 수
    private val koreanConsonant = arrayOf(
        'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ',
        'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'
    ) // 자음

    private val indexList = listOf(
        "ㄱ", "ㄱ", "ㄴ", "ㄷ", "ㄷ", "ㄹ", "ㅁ", "ㅂ", "ㅂ",
        "ㅅ", "ㅅ", "ㅇ", "ㅈ", "ㅈ", "ㅊ", "ㅋ", "ㅌ", "ㅍ", "ㅎ"
    )
    private val consonants = listOf(
        "ga", "na", "da", "la", "ma", "ba", "sa", "ah", "ja", "cha", "ka", "ta", "pa", "ha"
    )
    private val consonantMap = mapOf(
        "ga" to "ㄱ", "na" to "ㄴ", "da" to "ㄷ", "la" to "ㄹ", "ma" to "ㅁ", "ba" to "ㅂ",
        "sa" to "ㅅ", "ah" to "ㅇ", "ja" to "ㅈ", "cha" to "ㅊ", "ka" to "ㅋ", "ta" to "ㅌ",
        "pa" to "ㅍ", "ha" to "ㅎ"
    )

    // 초성인지 체크
    private fun isConsonant(char: Char) = koreanConsonant.contains(char)

    // 한글인지 체크
    private fun isKorean(char: Char) = char.code in KOREAN_UNICODE_START..KOREAN_UNICODE_END

    // 자음 얻기
    private fun getConsonant(char: Char): Char {
        val hasBegin = (char.code - KOREAN_UNICODE_START)
        val idx = hasBegin / KOREAN_UNICODE_BASED
        return koreanConsonant[idx]
    }

    // 자음 얻기 (쌍자음 제외)
    private fun getIndex(char: Char): String {
        if (!isKorean(char)) return char.toString()
        val hasBegin = (char.code - KOREAN_UNICODE_START)
        val idx = hasBegin / KOREAN_UNICODE_BASED
        return indexList[idx]
    }

    /**
     * 첫 자음 기준 필터
     * @param index 첫 자음 ex. ㄱ, ㄴ, ...
     * @param contactMap 필터 할 원본 Map
     */
    private fun filterByIndex(
        index: String,
        contactMap: MutableMap<Int, Contact.SweetieInfo>
    ): MutableList<Contact> {
        val filteredList = mutableListOf<Contact.SweetiesID>()

        for (contact in contactMap) {
            if (getIndex(contact.value.name.first()) == consonantMap[index]) {
                filteredList.add(Contact.SweetiesID(contact.key, contact.value))
            }
        }

        return if (filteredList.isNotEmpty()) {
            mutableListOf<Contact>().apply {
                add(Contact.ContactIndex(consonantMap[index]!!))
                addAll(filteredList)
            }
        } else mutableListOf()
    }

    /**
     * 첫 자음을 기준으로 그룹화 된 리사이클러뷰 목록 생성
     * @param map 그룹화 할 원본 Map
     */
    fun groupByIndex(map: MutableMap<Int, Contact.SweetieInfo>) =
        consonants.flatMap { filterByIndex(it, map) }

    /**
     * 초성 또는 한글 검색
     * @param based 비교 대상
     * @param search 검색 단어
     */
    fun matchKoreanAndConsonant(based: String, search: String): Boolean {
        var temp: Int
        val diffLength = based.length - search.length
        val searchLength = search.length

        if (diffLength < 0) {
            return false
        } else {
            for (i in 0..diffLength) {
                temp = 0

                while (temp < searchLength) {
                    if (isConsonant(search[temp]) && isKorean(based[i + temp])) {
                        // 현재 char이 초성이고 based가 한글일 때
                        if (getConsonant(based[i + temp]) == search[temp]) {
                            // 각각의 초성끼리 같은지 비교
                            temp++
                        } else {
                            break
                        }
                    } else {
                        // char이 초성이 아니라면
                        if (based[i + temp] == search[temp]) {
                            temp++
                        } else {
                            break
                        }
                    }
                }
                if (temp == searchLength) return true
            }
            return false
        }
    }
}