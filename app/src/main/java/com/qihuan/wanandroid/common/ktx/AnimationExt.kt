package com.qihuan.wanandroid.common.ktx

import android.animation.Animator
import android.view.ViewPropertyAnimator
import android.view.animation.Animation
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation

/**
 * AnimationExt
 * @author qi
 * @since 2020/8/13
 */
fun Animation.setAnimationListener(
    onAnimationStart: ((Animation) -> Unit)? = null,
    onAnimationRepeat: ((Animation) -> Unit)? = null,
    onAnimationEnd: (Animation) -> Unit
) {
    setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation) {
            onAnimationStart?.invoke(animation)
        }

        override fun onAnimationRepeat(animation: Animation) {
            onAnimationRepeat?.invoke(animation)
        }

        override fun onAnimationEnd(animation: Animation) {
            onAnimationEnd.invoke(animation)
        }
    })
}

fun ViewPropertyAnimator.setListener(
    onAnimationStart: ((Animator?) -> Unit)? = null,
    onAnimationRepeat: ((Animator?) -> Unit)? = null,
    onAnimationCancel: ((Animator?) -> Unit)? = null,
    onAnimationEnd: (Animator?) -> Unit
): ViewPropertyAnimator {
    return setListener(object : Animator.AnimatorListener {
        override fun onAnimationRepeat(animation: Animator?) {
            onAnimationRepeat?.invoke(animation)
        }

        override fun onAnimationEnd(animation: Animator?) {
            onAnimationEnd.invoke(animation)
        }

        override fun onAnimationCancel(animation: Animator?) {
            onAnimationCancel?.invoke(animation)
        }

        override fun onAnimationStart(animation: Animator?) {
            onAnimationStart?.invoke(animation)
        }
    })
}

fun listenForAllSpringsEnd(
    onEnd: (Boolean) -> Unit,
    vararg springs: SpringAnimation
) = MultiSpringEndListener(onEnd, *springs)

/**
 * A class which adds [DynamicAnimation.OnAnimationEndListener]s to the given `springs` and invokes
 * `onEnd` when all have finished.
 */
class MultiSpringEndListener(
    onEnd: (Boolean) -> Unit,
    vararg springs: SpringAnimation
) {
    private val listeners = ArrayList<DynamicAnimation.OnAnimationEndListener>(springs.size)

    private var wasCancelled = false

    init {
        springs.forEach {
            val listener = object : DynamicAnimation.OnAnimationEndListener {
                override fun onAnimationEnd(
                    animation: DynamicAnimation<out DynamicAnimation<*>>?,
                    canceled: Boolean,
                    value: Float,
                    velocity: Float
                ) {
                    animation?.removeEndListener(this)
                    wasCancelled = wasCancelled or canceled
                    listeners.remove(this)
                    if (listeners.isEmpty()) {
                        onEnd(wasCancelled)
                    }
                }
            }
            it.addEndListener(listener)
            listeners.add(listener)
        }
    }
}
