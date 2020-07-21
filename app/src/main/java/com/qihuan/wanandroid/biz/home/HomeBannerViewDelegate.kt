package com.qihuan.wanandroid.biz.home

import android.content.Context
import com.drakeet.multitype.ViewDelegate
import com.qihuan.wanandroid.bean.BannerList

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