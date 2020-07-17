package com.qihuan.wanandroid.home

import android.content.Context
import com.drakeet.multitype.ViewDelegate
import com.qihuan.wanandroid.common.bean.BannerList
import com.qihuan.wanandroid.common.view.HomeBannerLayout

/**
 * HomeBannerViewDelegate
 * @author qi
 * @since 2020/7/16
 */
class HomeBannerViewDelegate : ViewDelegate<BannerList, HomeBannerLayout>() {
    override fun onCreateView(context: Context): HomeBannerLayout {
        return HomeBannerLayout(context)
    }

    override fun onBindView(view: HomeBannerLayout, item: BannerList) {
        view.setData(item.list)
    }
}