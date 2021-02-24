package tech.jour.kotlinext

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment


fun Context.finish() {
    when (val currentContext = this) {
        is Activity -> currentContext.finish()
        is Fragment -> currentContext.requireActivity().finish()
    }
}

fun Context.toActivity(): AppCompatActivity? =
    when (val currentContext = this) {
        is AppCompatActivity -> currentContext
        is Fragment -> currentContext.requireActivity() as? AppCompatActivity
        else -> null
    }

