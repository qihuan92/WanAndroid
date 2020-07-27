package com.qihuan.wanandroid.biz.home

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
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.qihuan.wanandroid.bean.BannerBean
import com.qihuan.wanandroid.common.ktx.dp
import com.qihuan.wanandroid.common.ktx.load

/**
 * BannerLayout
 *
 * @author qi
 * @since 2020/7/16
 */
class HomeBannerLayout(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), DefaultLifecycleObserver {

    companion object {
        const val INCREASE_COUNT = 2
        const val SIDE_COUNT = 1
    }

    private lateinit var homeBannerAdapter: HomeBannerAdapter
    private lateinit var viewPager: ViewPager2
    private var infinite: Boolean = true

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
        viewPager = ViewPager2(context).apply {
            id = View.generateViewId()
            layoutParams = LayoutParams(0, 0)
        }
        addView(viewPager)

        val set = ConstraintSet()
        set.clone(this)
        set.connect(viewPager.id, ConstraintSet.TOP, id, ConstraintSet.TOP)
        set.connect(viewPager.id, ConstraintSet.BOTTOM, id, ConstraintSet.BOTTOM)
        set.connect(viewPager.id, ConstraintSet.LEFT, id, ConstraintSet.LEFT)
        set.connect(viewPager.id, ConstraintSet.RIGHT, id, ConstraintSet.RIGHT)
        set.setDimensionRatio(viewPager.id, "H,16:9")
        set.applyTo(this)
    }

    private fun setUpView() {
        homeBannerAdapter = HomeBannerAdapter(infinite = infinite)

        viewPager.apply {
            adapter = homeBannerAdapter
            registerOnPageChangeCallback(OnPageChangeCallback(infinite, this, homeBannerAdapter))
        }
    }

    fun setData(bannerList: List<BannerBean>) {
        homeBannerAdapter.setData(bannerList)
        viewPager.setCurrentItem(SIDE_COUNT, false)
    }

    class HomeBannerAdapter(
        private val itemList: MutableList<BannerBean> = mutableListOf(),
        private val infinite: Boolean
    ) : RecyclerView.Adapter<HomeBannerAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                BannerView(parent.context)
            )
        }

        override fun getItemCount(): Int {
            return getItemCountInternal()
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val data = itemList[getRealPosition(position)]
            holder.bind(data)
        }

        fun setData(bannerList: List<BannerBean>) {
            itemList.clear()
            if (bannerList.isNotEmpty()) {
                itemList.addAll(bannerList)
            }
            notifyDataSetChanged()
        }

        fun getRealItemCount(): Int {
            return itemList.size
        }

        private fun getItemCountInternal(): Int {
            return if (infinite) {
                if (getRealItemCount() > 1) {
                    getRealItemCount() + INCREASE_COUNT
                } else {
                    getRealItemCount()
                }
            } else {
                getRealItemCount()
            }
        }

        private fun getRealPosition(position: Int): Int {
            var realPosition = 0
            if (getRealItemCount() > 1) {
                realPosition = (position - SIDE_COUNT) % getRealItemCount()
            }
            if (realPosition < 0) {
                realPosition += getRealItemCount()
            }
            return realPosition
        }

        class ViewHolder(private val bannerView: BannerView) : RecyclerView.ViewHolder(bannerView) {
            fun bind(bannerBean: BannerBean) {
                bannerView.ivBanner.load(bannerBean.imagePath)
            }
        }
    }

    inner class OnPageChangeCallback(
        private val infinite: Boolean,
        private val viewPager: ViewPager2,
        private val adapter: HomeBannerAdapter
    ) : ViewPager2.OnPageChangeCallback() {
        private var tempPosition: Int = 0
        override fun onPageSelected(position: Int) {
            if (adapter.getRealItemCount() > 1) {
                tempPosition = position
            }
        }

        override fun onPageScrollStateChanged(state: Int) {
            super.onPageScrollStateChanged(state)
            when (state) {
                ViewPager2.SCROLL_STATE_DRAGGING -> {
                    if (infinite) {
                        if (tempPosition == SIDE_COUNT - 1) {
                            viewPager.setCurrentItem(
                                adapter.getRealItemCount() + tempPosition,
                                false
                            )
                        } else if (tempPosition == adapter.getRealItemCount() + SIDE_COUNT) {
                            viewPager.setCurrentItem(SIDE_COUNT, false)
                        }
                    }
                }
                else -> {
                }
            }
        }
    }

    class BannerView(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
    ) : CardView(context, attrs, defStyleAttr) {
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

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        startPlay()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        stopPlay()
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        startPlay()
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        stopPlay()
    }

    private fun startPlay() {
        //postDelayed(bannerRunnable, period)
    }

    private fun stopPlay() {
        //removeCallbacks(bannerRunnable)
    }
}