package tech.jour.kotlinext

import android.graphics.Color

//传入格式为十六进制颜色代码
// #00CCFF
fun String.toColor(): Int = Color.parseColor(this)

fun Long.friendlyTime(): String {
    var dateString = ""
    val days = this / (1000 * 60 * 60 * 24)
    dateString += if (days > 0) "${days}天" else ""
    val hours = this % (1000 * 60 * 60 * 24) / (1000 * 60 * 60)
    dateString += if (hours > 0) "${hours}小时" else ""
    val minutes = this % (1000 * 60 * 60) / (1000 * 60)
    dateString += if (minutes > 0) "${minutes}分" else ""
    return dateString
//    return (days.toString() + "天" + hours + "时" + minutes + "分")
}