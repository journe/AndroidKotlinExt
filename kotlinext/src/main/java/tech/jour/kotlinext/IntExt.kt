package tech.jour.kotlinext

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.text.DecimalFormat

/**
 * Created by journey on 2020/5/27.
 */
fun Int.countStr(): String {
    return if (this > 9999) {
        val df = DecimalFormat("0.0")
        "${df.format(this.toDouble() / 10000)}万"
    } else {
        this.toString()
    }
}

fun Int.resourceToBitmap(context: Context):Bitmap = BitmapFactory.decodeResource(context.resources, this)

fun Int.resourceToString(context: Context):String = context.getString(this)