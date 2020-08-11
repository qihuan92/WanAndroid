package com.qihuan.wanandroid.widget

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.qihuan.wanandroid.R
import com.qihuan.wanandroid.common.ktx.dp

/**
 * StateLayout
 * @author qi
 * @since 2020/8/11
 */
@Suppress("unused", "MemberVisibilityCanBePrivate")
class StateLayout : FrameLayout {
    enum class State {
        NONE,
        LOADING,
        CONTENT,
        EMPTY,
        ERROR
    }

    inner class SwitchTask(private var target: View) : Runnable {
        override fun run() {
            for (i in 0..childCount) {
                if (state == State.LOADING && getChildAt(i) == contentView) continue
                hideAnim(getChildAt(i))
            }
            showAnim(target)
        }
    }

    var state = State.NONE
    private val loadingView by lazy {
        FrameLayout(context).apply {
            layoutParams = LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            ).apply {
                gravity = Gravity.CENTER
            }

            addView(ProgressBar(context))
        }
    }
    private val emptyView by lazy {
        FrameLayout(context).apply {
            layoutParams = LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            ).apply {
                gravity = Gravity.CENTER
            }

            addView(
                ImageView(context).apply {
                    layoutParams = LayoutParams(60f.dp, 60f.dp)
                    setImageResource(R.drawable.ic_round_inbox_24)
                }
            )
        }
    }
    private val errorView by lazy {
        FrameLayout(context).apply {
            layoutParams = LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            ).apply {
                gravity = Gravity.CENTER
            }

            addView(
                ImageView(context).apply {
                    layoutParams = LayoutParams(60f.dp, 60f.dp)
                    setImageResource(R.drawable.ic_round_error_outline_24)
                }
            )
        }
    }

    private lateinit var contentView: View
    private var animDuration = 250L
    private var noDataText: String = "暂无数据"
    private var mLoadingText: String = "加载中"
    private var switchTask: SwitchTask? = null
    private var mRetryAction: ((errView: View) -> Unit)? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    fun wrap(view: View?): StateLayout {
        if (view == null) {
            throw IllegalArgumentException("view can not be null")
        }

        prepareStateView()

        view.visibility = View.INVISIBLE
        view.alpha = 0f
        if (view.parent == null) {
            //no attach parent.
            addView(view, 0, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT))
            contentView = view
        } else {
            // 1.remove self from parent
            val parent = view.parent as ViewGroup
            val lp = view.layoutParams
            val index = parent.indexOfChild(view)
            parent.removeView(view)
            // 2.wrap view as a parent
            addView(view, 0, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT))

            // 3.add this to original parent，暂不支持parent为ConstraintLayout
            parent.addView(this, index, lp)
            contentView = view
        }
        switchLayout(State.CONTENT)
        return this
    }

    fun wrap(activity: Activity): StateLayout =
        wrap((activity.findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0))

    fun wrap(fragment: Fragment): StateLayout = wrap(fragment.view)

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (childCount > 0) {
            contentView = getChildAt(0)
            prepareStateView()
            switchLayout(State.CONTENT)
        }
    }

    private fun prepareStateView() {
        if (emptyView.parent == null) {
            with(emptyView) {
                visibility = View.INVISIBLE
                alpha = 0f
            }
            addView(emptyView)
        }

        if (errorView.parent == null) {
            with(errorView) {
                visibility = View.INVISIBLE
                alpha = 0f
                setOnClickListener { retry() }
            }
            addView(errorView)
        }

        if (loadingView.parent == null) {
            with(loadingView) {
                visibility = View.INVISIBLE
                alpha = 0f
            }
            addView(loadingView)
        }
    }

    private fun switchLayout(s: State) {
        if (state == s) return
        state = s
        when (state) {
            State.LOADING -> {
                switch(loadingView)
            }
            State.EMPTY -> {
                switch(emptyView)
            }
            State.ERROR -> {
                switch(errorView)
            }
            State.CONTENT -> {
                switch(contentView)
            }
            else -> {

            }
        }
    }

    fun showLoading(): StateLayout {
        switchLayout(State.LOADING)
        return this
    }

    fun showContent(): StateLayout {
        switchLayout(State.CONTENT)
        return this
    }

    fun showEmpty(): StateLayout {
        switchLayout(State.EMPTY)
        return this
    }

    fun showError(): StateLayout {
        switchLayout(State.ERROR)
        return this
    }

    private fun switch(view: View) {
        if (switchTask != null) {
            removeCallbacks(switchTask)
        }
        switchTask = SwitchTask(view)
        post(switchTask)
    }

    private fun retry() {
        showLoading()
        handler.postDelayed({
            mRetryAction?.invoke(errorView)
        }, animDuration)
    }

    private fun showAnim(v: View?) {
        if (v == null) return
        v.animate().cancel()
        v.animate().alpha(1f).setDuration(animDuration)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator?) {
                    v.visibility = View.VISIBLE
                }
            })
            .start()
    }

    private fun hideAnim(v: View?) {
        if (v == null) return
        v.animate().cancel()
        v.animate().alpha(0f).setDuration(animDuration)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    v.visibility = View.INVISIBLE
                }
            })
            .start()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (state == State.LOADING && loadingView.visibility == View.VISIBLE) return true
        return super.dispatchTouchEvent(ev)
    }
}

