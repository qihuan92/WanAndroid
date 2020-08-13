package com.qihuan.wanandroid.common.ktx

import android.animation.Animator
import android.view.ViewPropertyAnimator
import android.view.animation.Animation

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