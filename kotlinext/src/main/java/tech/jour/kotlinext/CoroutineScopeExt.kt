package tech.jour.kotlinext

import android.util.Log
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

fun CoroutineScope.launchThrowException(block: suspend CoroutineScope.() -> Unit) {
    launch {
        try {
            block.invoke(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

fun launchCoroutine(
    block: suspend (CoroutineScope) -> Unit,
    error: ((e: Exception) -> Unit)? = null,
    context: CoroutineContext = Dispatchers.Main
): Job {
    return GlobalScope.launch(context + CoroutineExceptionHandler { _, e ->
        Log.e("==>coroutineException", e.message ?: "")
    }) {
        try {
            block(this)
        } catch (e: Exception) {
            Log.e("==>coroutineError", e.message ?: "")
            if (error != null) {
                error(e)
            }
        }
    }
}
