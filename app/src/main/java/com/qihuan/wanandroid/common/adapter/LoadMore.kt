package com.qihuan.wanandroid.common.adapter

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.core.view.setPadding
import com.drakeet.multitype.ViewDelegate
import com.qihuan.wanandroid.common.ktx.dp

/**
 * LoadMoreViewDelegate
 * @author qi
 * @since 2020/8/7
 */
class LoadMoreViewDelegate : ViewDelegate<LoadMoreItem, LoadMoreView>() {
    override fun onCreateView(context: Context): LoadMoreView {
        return LoadMoreView(context)
    }

    override fun onBindView(view: LoadMoreView, item: LoadMoreItem) {

    }
}

/**
 * LoadMoreItem
 * @author qi
 * @since 2020/8/7
 */
class LoadMoreItem

/**
 * LoadMoreView
 * @author qi
 * @since 2020/8/7
 */
class LoadMoreView(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT).apply {
            gravity = Gravity.CENTER
        }
        setPadding(5f.dp)
        addView(progressView(), layoutParams)
    }

    private fun progressView(): ProgressBar {
        return ProgressBar(context, null, android.R.attr.progressBarStyleSmall)
    }
}