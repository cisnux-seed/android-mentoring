package dev.cisnux.dicodingmentoring.utils


data class CheckBoxItem(
    val title: String,
    val checked: Boolean,
    val onCheckedChange: (checked: Boolean, title: String) -> Unit
)