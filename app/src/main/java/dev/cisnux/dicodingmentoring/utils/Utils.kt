package dev.cisnux.dicodingmentoring.utils

const val BASE_URL = "https://www.mentoring.cisnux.xyz/"

fun String.isEmail(): Boolean {
    val emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\$"
    return Regex(emailRegex).matches(this)
}

fun String.isPasswordSecure(): Boolean = this.trim().length >= 8