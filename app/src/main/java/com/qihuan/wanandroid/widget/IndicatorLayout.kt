package com.qihuan.wanandroid.widget

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import com.qihuan.wanandroid.R
import com.qihuan.wanandroid.common.ktx.dp

/**
 * IndicatorLayout
 * @author qi
 * @date 2018/10/23
 */
@Suppress("unused")
class IndicatorLayout(
    context: Context?, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attributeSet, defStyleAttr) {

    @DrawableRes
    private var dotDrawableResId: Int = 0
    private var totalSize: Int = 0
    private var selectedPosition: Int = 0

    init {
        orientation = HORIZONTAL
        gravity = Gravity.CENTER_VERTICAL or Gravity.CENTER_HORIZONTAL
    }

    fun refresh(
        @DrawableRes drawableResId: Int = R.drawable.selector_banner_point,
        totalSize: Int,
        dotMarginHorizontal: Int = 3f.dp,
        dotMarginVertical: Int = 3f.dp
    ) {
        this.dotDrawableResId = drawableResId
        this.totalSize = totalSize

        removeAllViews()
        var dotView: ImageView
        val layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        layoutParams.setMargins(
            dotMarginHorizontal,
            dotMarginVertical,
            dotMarginHorizontal,
            dotMarginVertical
        )
        for (i in 0 until totalSize) {
            dotView = ImageView(context)
            dotView.layoutParams = layoutParams
            dotView.setImageResource(drawableResId)
            addView(dotView)
        }

        setSelectedPosition(0)
    }

    fun setSelectedPosition(position: Int) {
        this.selectedPosition = position
        for (i in 0 until totalSize) {
            getChildAt(i).isEnabled = i == position
            getChildAt(i).requestLayout()
        }
    }
}