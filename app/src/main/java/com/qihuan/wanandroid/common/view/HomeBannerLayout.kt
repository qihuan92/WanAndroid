package com.qihuan.wanandroid.common.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import com.qihuan.wanandroid.common.bean.BannerBean
import com.qihuan.wanandroid.common.ktx.dp
import com.qihuan.wanandroid.common.ktx.load
import com.youth.banner.Banner
import com.youth.banner.adapter.BannerAdapter

/**
 * BannerLayout
 * todo wip
 * @author qi
 * @since 2020/7/16
 */
class HomeBannerLayout(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    ConstraintLayout(context, attrs, defStyleAttr) {

    private lateinit var homeBannerAdapter: HomeBannerAdapter
    private lateinit var homeBanner: Banner<BannerBean, HomeBannerAdapter>

    init {
        initView()
    }

    private fun initView() {
        id = View.generateViewId()
        layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        initViewPager()
        setUpView()
    }

    private fun initViewPager() {
        homeBanner = Banner(context)
        homeBanner.id = View.generateViewId()
        homeBanner.layoutParams = LayoutParams(0, 0)
        addView(homeBanner)

        val set = ConstraintSet()
        set.clone(this)
        set.connect(homeBanner.id, ConstraintSet.TOP, id, ConstraintSet.TOP)
        set.connect(homeBanner.id, ConstraintSet.BOTTOM, id, ConstraintSet.BOTTOM)
        set.connect(homeBanner.id, ConstraintSet.LEFT, id, ConstraintSet.LEFT)
        set.connect(homeBanner.id, ConstraintSet.RIGHT, id, ConstraintSet.RIGHT)
        set.setDimensionRatio(homeBanner.id, "H,16:9")
        set.applyTo(this)
    }

    private fun setUpView() {
        homeBannerAdapter = HomeBannerAdapter()
        homeBanner.adapter = homeBannerAdapter


    }

    fun setData(bannerList: List<BannerBean>) {
        homeBannerAdapter.setData(bannerList)
    }

    class HomeBannerAdapter(private var itemList: MutableList<BannerBean> = mutableListOf()) :
        BannerAdapter<BannerBean, HomeBannerAdapter.ViewHolder>(itemList) {

        override fun onCreateHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(BannerView(parent.context))
        }

        override fun onBindView(holder: ViewHolder?, data: BannerBean, position: Int, size: Int) {
            holder?.bindTo(data)
        }

        class ViewHolder(private val bannerView: BannerView) : RecyclerView.ViewHolder(bannerView) {
            fun bindTo(bannerBean: BannerBean) {
                bannerView.ivBanner.load(bannerBean.imagePath)
            }
        }

        fun setData(bannerList: List<BannerBean>) {
            this.itemList = bannerList.toMutableList()
            notifyDataSetChanged()
        }

        class BannerView(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
            CardView(context, attrs, defStyleAttr) {
            val ivBanner: ImageView

            init {
                layoutParams = MarginLayoutParams(MATCH_PARENT, MATCH_PARENT).apply {
                    setMargins(15f.dp, 10f.dp, 15f.dp, 10f.dp)
                }
                preventCornerOverlap = false

                addView(
                    ConstraintLayout(context).apply {
                        id = View.generateViewId()
                        layoutParams = ConstraintLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)

                        ivBanner = ImageView(context).apply {
                            id = View.generateViewId()
                            layoutParams = ConstraintLayout.LayoutParams(0, 0)
                        }
                        addView(ivBanner)
                        val ivSet = ConstraintSet()
                        ivSet.clone(this)
                        ivSet.connect(ivBanner.id, ConstraintSet.TOP, id, ConstraintSet.TOP)
                        ivSet.connect(ivBanner.id, ConstraintSet.BOTTOM, id, ConstraintSet.BOTTOM)
                        ivSet.connect(ivBanner.id, ConstraintSet.LEFT, id, ConstraintSet.LEFT)
                        ivSet.connect(ivBanner.id, ConstraintSet.RIGHT, id, ConstraintSet.RIGHT)
                        ivSet.applyTo(this)
                    }
                )
            }
        }
    }
}