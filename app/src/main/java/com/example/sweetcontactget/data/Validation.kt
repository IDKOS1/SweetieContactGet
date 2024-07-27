package com.example.sweetcontactget.data
import java.util.regex.Pattern

//이름 유효성
fun isRegularName(name: String): Boolean {
    val namePattern = "^[a-zA-Z0-9가-힣ㄱ-ㅎㅏ-ㅣ]*$"
    val pattern = Pattern.matches(namePattern,name)
    return pattern
}

//전화번호 유효성
fun isRegularPhoneNumber(number: String): Boolean {
    val phoneNumberPattern = "^[0-9-]+$"
    val pattern = Pattern.matches(phoneNumberPattern,number)
    return pattern
}

//이벤트 정보 유효성
fun isRegularEvent(event: String): Boolean {
    val eventPattern = "^[A-Za-z0-9가-힣ㄱ-ㅎㅏ-ㅣ\$@!%*#?&.]{2,10}$"
    val pattern = Pattern.matches(eventPattern, event)
    return pattern
}

//관계 유효성
fun isRegularRelationShip(relation : String): Boolean {
    val reationPattern = "(친구|직장|학교|가족)"
    val pattern = Pattern.matches(reationPattern,relation)
    return pattern
}

//메모 유효성
fun isRegularMemo(memo: String): Boolean {
    val eventPattern = "^[A-Za-z0-9가-힣ㄱ-ㅎㅏ-ㅣ\$@!%*#?&.]{2,8}$"
    val pattern = Pattern.matches(eventPattern, memo)
    return pattern
}

//전화번호 입력시 하이픈 로직을 위한..지역번호 처리

fun formatPhoneNumber(text: String): String {
    if (text.startsWith("02")) {
        return when (text.length) {
            in 1..2 -> text
            in 3..5 -> "${text.substring(0, 2)}-${text.substring(2)}"
            in 6..9 -> "${text.substring(0, 2)}-${text.substring(2, 5)}-${text.substring(5)}"
            in 10 ..10 -> "${text.substring(0, 2)}-${text.substring(2, 6)}-${text.substring(6)}"
            else -> text
        }
    } else {
        return when (text.length){
            in 1..3 -> text
            in 4..6 -> "${text.substring(0, 3)}-${text.substring(3)}"
            in 7..10 -> "${text.substring(0, 3)}-${text.substring(3, 6)}-${text.substring(6)}"
            in 11 ..11 -> "${text.substring(0, 3)}-${text.substring(3, 7)}-${text.substring(7)}"
            else -> text
        }
    }
}