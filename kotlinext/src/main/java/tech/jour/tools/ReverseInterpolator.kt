package tech.jour.tools

import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator

/**
 * Created by journey on 4/21/21.
 *
 * https://stackoverflow.com/questions/4120824/reversing-an-animation
 * 动画倒放的插值器
 *
 */
class ReverseInterpolator @JvmOverloads constructor(private val delegate: Interpolator = LinearInterpolator()) :
    Interpolator {
    override fun getInterpolation(input: Float): Float {
        return 1 - delegate.getInterpolation(input)
    }
}