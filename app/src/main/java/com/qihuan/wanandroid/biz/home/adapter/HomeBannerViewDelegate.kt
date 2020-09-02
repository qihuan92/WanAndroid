package com.qihuan.wanandroid.biz.home.adapter

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.drakeet.multitype.ViewDelegate
import com.qihuan.wanandroid.bean.BannerList
import com.qihuan.wanandroid.biz.home.HomeBannerLayout

/**
 * HomeBannerViewDelegate
 * @author qi
 * @since 2020/7/16
 */
class HomeBannerViewDelegate(
    private val lifecycleOwner: LifecycleOwner
) : ViewDelegate<BannerList, HomeBannerLayout>() {
    override fun onCreateView(context: Context): HomeBannerLayout {
        val homeBannerLayout = HomeBannerLayout(context)
        lifecycleOwner.lifecycle.addObserver(homeBannerLayout)
        return homeBannerLayout
    }

    override fun onBindView(view: HomeBannerLayout, item: BannerList) {
        view.setData(item.list)
    }
}