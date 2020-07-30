package com.qihuan.wanandroid.widget

import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment

/**
 * TabContainer
 * @author qi
 * @since 2020/6/28
 */
interface TabContainer {

    fun title(): String

    @DrawableRes
    fun icon(): Int

    fun createFragment(): Fragment
}