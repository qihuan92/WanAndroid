@file:Suppress("unused")

package com.qihuan.wanandroid.common.ktx

import android.graphics.Bitmap
import android.graphics.Color
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.palette.graphics.Palette
import coil.load
import coil.size.Scale

/**
 * ImageUtils
 * @author qi
 * @date 2018/5/31
 */
fun ImageView.load(url: String, @DrawableRes placeholder: Int = android.R.color.transparent) {
    load(url) {
        crossfade(resources.getInteger(android.R.integer.config_mediumAnimTime))
        placeholder(placeholder)
        scale(Scale.FIT)
    }
}

/**
 * 根据bitmap提取颜色
 *
 * @return
 */
fun Bitmap.getSwatchColor(): Int {
    val p = Palette.from(this).generate()
    val swatchMuted = p.darkMutedSwatch
    val swatchVibrant = p.darkVibrantSwatch
    return swatchMuted?.rgb ?: (swatchVibrant?.rgb ?: Color.parseColor("#ABB0BE"))
}