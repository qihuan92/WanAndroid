package com.qihuan.wanandroid.home

import android.content.Context
import com.drakeet.multitype.ViewDelegate
import com.qihuan.wanandroid.common.bean.BannerList
import com.qihuan.wanandroid.common.view.BannerLayout

/**
 * HomeBannerViewDelegate
 * @author qi
 * @since 2020/7/16
 */
class HomeBannerViewDelegate : ViewDelegate<BannerList, BannerLayout>() {
    override fun onCreateView(context: Context): BannerLayout {
        return BannerLayout(context)
    }

    override fun onBindView(view: BannerLayout, item: BannerList) {
        TODO("Not yet implemented")
    }
}