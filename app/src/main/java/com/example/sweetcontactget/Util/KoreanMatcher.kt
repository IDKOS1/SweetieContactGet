package com.example.sweetcontactget.Util

object KoreanMatcher {
    private const val KOREAN_UNICODE_START = 44032 // 가
    private const val KOREAN_UNICODE_END = 55203   // 힣
    private const val KOREAN_UNICODE_BASED = 588   // 각 자음 마다 가지는 글자 수
    private val koreanConsonant = arrayOf(
        'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ',
        'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'
    ) // 자음

    // 초성인지 체크
    private fun isConsonant(ch: Char) = koreanConsonant.contains(ch)

    // 한글인지 체크
    private fun isKorean(ch: Char) = ch.code in KOREAN_UNICODE_START..KOREAN_UNICODE_END

    // 자음 얻기
    private fun getConsonant(ch: Char): Char {
        val hasBegin = (ch.code - KOREAN_UNICODE_START)
        val idx = hasBegin / KOREAN_UNICODE_BASED
        return koreanConsonant[idx]
    }

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