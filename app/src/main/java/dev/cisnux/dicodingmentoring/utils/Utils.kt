package dev.cisnux.dicodingmentoring.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

const val BASE_URL = "https://www.mentoring.cisnux.xyz/"

fun String.isEmail(): Boolean {
    val emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\$"
    return Regex(emailRegex).matches(this)
}

fun String.isPasswordSecure(): Boolean = this.trim().length >= 8

fun String.isValidAbout(maxLength: Int) = length <= maxLength

fun String.asList() = replace("[^\\S\r\n]+".toRegex(), "").split("\n")

fun Long.withDateFormat(): String {
    val date = Date(this)
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
    return DateFormat.getDateInstance(DateFormat.FULL).format(date)
}