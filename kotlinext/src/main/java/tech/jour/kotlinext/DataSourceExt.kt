package tech.jour.kotlinext

/**
 * Created by journey on 2019-12-27.
 */

inline fun <reified T : Any> DataResult<T>.result(
    onComplete: (t: DataResult.Success<T>) -> Unit,
    onError: (t: DataResult.Error) -> Unit
) {
    when (this) {
        is DataResult.Success<T> -> {
            onComplete(this)
        }
        is DataResult.Error -> {
            onError(this)
        }
    }
}
