package tech.jour.kotlinext

import android.animation.Animator
import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.EditText
import androidx.core.view.ViewCompat
import androidx.viewpager2.widget.ViewPager2
import kotlinx.coroutines.*
import org.jetbrains.anko.dip
import kotlin.coroutines.CoroutineContext

/**
 * Created by journey on 2019-12-26.
 */
/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}

class AutoDisposableJob(
    private val view: View,
    private val wrapped: Job
) : Job by wrapped, View.OnAttachStateChangeListener {

    init {
        if (ViewCompat.isAttachedToWindow(view)) {
            view.addOnAttachStateChangeListener(this)
        } else {
            cancel()
        }
        invokeOnCompletion {
            view.removeOnAttachStateChangeListener(this)
        }
    }

    override fun onViewDetachedFromWindow(v: View?) {
        cancel()
        view.removeOnAttachStateChangeListener(this)
    }

    override fun onViewAttachedToWindow(v: View?) = Unit

}

fun Job.autoDispose(view: View) =
    AutoDisposableJob(view, this)

private var lastClickTime: Long = 0
fun View.onClick(
    context: CoroutineContext = Dispatchers.Main,
    handler: suspend CoroutineScope.(v: View?) -> Unit
) {
    setOnClickListener { v ->
        GlobalScope.launch(context, CoroutineStart.DEFAULT) {
            handler(v)
        }.autoDispose(v)
    }
    val time = System.currentTimeMillis()
    //防止双击,间隔由interval控制
    if (time - lastClickTime > 500) {

    }
    lastClickTime = time
}


/*** 可见性相关 ****/
fun View.gone() {
    visibility = View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.popup() {
    animate().translationY(-0f)
}

fun View.animateY(y: Int) {
    animate().translationY(dip(y).toFloat())
}

val View.isGone: Boolean
    get() {
        return visibility == View.GONE
    }

val View.isVisible: Boolean
    get() {
        return visibility == View.VISIBLE
    }

val View.isInvisible: Boolean
    get() {
        return visibility == View.INVISIBLE
    }

/**
 * 切换View的可见性
 */
fun View.toggleVisibility() {
    visibility = if (visibility == View.GONE) View.VISIBLE else View.GONE
}

fun ViewPager2.setCurrentItem(
    item: Int,
    duration: Long,
    interpolator: TimeInterpolator = AccelerateDecelerateInterpolator(),
    pagePxWidth: Int = width // Default value taken from getWidth() from ViewPager2 view
) {
    val pxToDrag: Int = pagePxWidth * (item - currentItem)
    val animator = ValueAnimator.ofInt(0, pxToDrag)
    var previousValue = 0
    animator.addUpdateListener { valueAnimator ->
        val currentValue = valueAnimator.animatedValue as Int
        val currentPxToDrag = (currentValue - previousValue).toFloat()
        fakeDragBy(-currentPxToDrag)
        previousValue = currentValue
    }
    animator.addListener(object : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator?) {
            beginFakeDrag()
        }

        override fun onAnimationEnd(animation: Animator?) {
            endFakeDrag()
        }

        override fun onAnimationCancel(animation: Animator?) { /* Ignored */
        }

        override fun onAnimationRepeat(animation: Animator?) { /* Ignored */
        }
    })
    animator.interpolator = interpolator
    animator.duration = duration
    animator.start()
}