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

//将请求返回的json格式化
fun String.httpLog(): String {
    var message = this
    val mMessage = StringBuffer()
    if (message.startsWith("--> POST")) {
        mMessage.setLength(0)
    }
    // 以{}或者[]形式的说明是响应结果的json数据，需要进行格式化
    if (message.startsWith("{") && message.endsWith("}")
        || message.startsWith("[") && message.endsWith("]")
    ) {
        message = message.formatJson()
    }
    mMessage.append(message)
    mMessage.append("\n")
    // 请求或者响应结束，打印整条日志
    if (message.startsWith("<-- END HTTP")) {
        return mMessage.toString()
        mMessage.setLength(0)
    }
    return message
}

//格式化json字符串
fun String.formatJson(): String {
    val jsonStr = this
    val sb = StringBuilder()
    var last = '\u0000'
    var current = '\u0000'
    var indent = 0
    for (element in jsonStr) {
        last = current
        current = element
        when (current) {
            '{', '[' -> {
                sb.append(current)
                sb.append('\n')
                indent++
                sb.addIndentBlank(indent)
            }
            '}', ']' -> {
                sb.append('\n')
                indent--
                sb.addIndentBlank(indent)
                sb.append(current)
            }
            ',' -> {
                sb.append(current)
                if (last != '\\') {
                    sb.append('\n')
                    sb.addIndentBlank(indent)
                }
            }
            else -> sb.append(current)
        }
    }
    return sb.toString()
}

//添加space
fun java.lang.StringBuilder.addIndentBlank(indent: Int): java.lang.StringBuilder {
    for (i in 0 until indent) {
        this.append('\t')
    }
    return this
}