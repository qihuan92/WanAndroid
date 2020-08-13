@file:Suppress("unused")

package com.qihuan.wanandroid.common.ktx

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.palette.graphics.Palette
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.qihuan.wanandroid.common.net.GlideApp
import com.qihuan.wanandroid.common.net.GlideRequest

/**
 * ImageUtils
 * @author qi
 * @date 2018/5/31
 */
fun ImageView.load(
    url: String,
    radius: Float = 0f,
    @DrawableRes placeholder: Int = android.R.color.transparent,
    listener: ((resource: Bitmap?) -> Unit)? = null
) {
    if (listener != null) {
        GlideApp.with(context)
            .asBitmap()
            .load(url)
            .placeholder(placeholder)
            .transform(MultiTransformation(CenterCrop(), RoundedCorners(radius.dp)))
            .listener(object : RequestListener<Bitmap> {
                override fun onResourceReady(
                    resource: Bitmap?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    listener.invoke(resource)
                    return false
                }

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }
            })
            .into(this)
        return
    }
    if (radius > 0) {
        get(url).placeholder(placeholder)
            .transform(MultiTransformation(CenterCrop(), RoundedCorners(radius.dp)))
            .into(this)
        return
    }
    get(url).placeholder(placeholder)
        .centerCrop()
        .into(this)
}

fun ImageView.load(
    drawable: Drawable,
    radius: Float = 0f,
    @DrawableRes placeholder: Int = android.R.color.transparent
) {
    if (radius > 0) {
        get(drawable).placeholder(placeholder)
            .transform(MultiTransformation(CenterCrop(), RoundedCorners(radius.dp)))
            .into(this)
        return
    }
    get(drawable).placeholder(placeholder)
        .centerCrop()
        .into(this)
}

fun ImageView.get(url: String): GlideRequest<Drawable> =
    GlideApp.with(context).load(url).transition(
        DrawableTransitionOptions.withCrossFade()
    )

fun ImageView.get(drawable: Drawable): GlideRequest<Drawable> =
    GlideApp.with(context).load(drawable).transition(DrawableTransitionOptions.withCrossFade())

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