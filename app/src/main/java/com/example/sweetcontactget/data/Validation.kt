package com.example.sweetcontactget.data

import android.content.Context
import android.widget.Toast
import java.util.regex.Pattern

//이름 유효성
fun isRegularName(name: String): Boolean {
    val namePattern = "^[a-zA-Z가-힣]*\$"
    val pattern = Pattern.matches(namePattern,name)
    return pattern
}

//전화번호 유효성
fun isRegularPhoneNumber(number: String): Boolean {
    val phoneNumberPattern = "^\\d{3}-\\d{3,4}-\\d{4}$"
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