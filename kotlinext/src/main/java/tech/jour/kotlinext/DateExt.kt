package tech.jour.kotlinext

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by journey on 2020/6/2.
 */
fun Date.string(format: String = "yyyy-MM-dd HH:mm"): String? {
    return SimpleDateFormat(format, Locale.getDefault()).format(this)
}