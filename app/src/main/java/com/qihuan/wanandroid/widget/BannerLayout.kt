package com.qihuan.wanandroid.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.progressindicator.ProgressIndicator
import com.qihuan.wanandroid.databinding.BannerLayoutBinding


/**
 * BannerLayout
 *
 * @author qi
 * @since 2020/7/16
 */
class BannerLayout(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), DefaultLifecycleObserver {

    companion object {
        const val INCREASE_COUNT = 2
        const val SIDE_COUNT = 1
    }

    private lateinit var binding: BannerLayoutBinding
    private lateinit var homeBannerAdapter: BannerAdapterWrapper
//    private lateinit var viewPager: ViewPager2
//    private lateinit var indicatorLayout: IndicatorLayout
    private var infinite = true
    private var autoPlay = true
    private var period = 3000L
    private var bannerRunnable: BannerRunnable? = null

    init {
        initView()
    }

    private fun initView() {
        binding = BannerLayoutBinding.bind(this)
//        initViewPager()
//        initIndicator()
    }

//    private fun initViewPager() {
//        viewPager = ViewPager2(context).apply {
//            id = View.generateViewId()
//            layoutParams = LayoutParams(0, 0)
//        }
//        addView(viewPager)
//
//        val set = ConstraintSet()
//        set.clone(this)
//        set.connect(viewPager.id, ConstraintSet.TOP, id, ConstraintSet.TOP)
//        set.connect(viewPager.id, ConstraintSet.BOTTOM, id, ConstraintSet.BOTTOM)
//        set.connect(viewPager.id, ConstraintSet.LEFT, id, ConstraintSet.LEFT)
//        set.connect(viewPager.id, ConstraintSet.RIGHT, id, ConstraintSet.RIGHT)
//        set.setDimensionRatio(viewPager.id, "H,16:9")
//        set.applyTo(this)
//    }

//    private fun initIndicator() {
//        indicatorLayout = IndicatorLayout(context).apply {
//            id = View.generateViewId()
//            layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
//                setMargins(15f.dp)
//            }
//        }
//        addView(indicatorLayout)
//
//        val set = ConstraintSet()
//        set.clone(this)
//        //set.connect(dotsIndicator.id, ConstraintSet.TOP, viewPager.id, ConstraintSet.BOTTOM)
//        set.connect(indicatorLayout.id, ConstraintSet.BOTTOM, viewPager.id, ConstraintSet.BOTTOM)
//        set.connect(indicatorLayout.id, ConstraintSet.LEFT, id, ConstraintSet.LEFT)
//        set.connect(indicatorLayout.id, ConstraintSet.RIGHT, id, ConstraintSet.RIGHT)
//        set.applyTo(this)
//    }

    fun setAdapter(adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) {
        homeBannerAdapter = BannerAdapterWrapper(infinite, adapter)

        binding.vpBanner.adapter = homeBannerAdapter
        binding.vpBanner.registerOnPageChangeCallback(
            OnPageChangeCallback(
                infinite,
                binding.vpBanner,
                homeBannerAdapter,
                binding.indicatorBanner
            )
        )
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
        if (!autoPlay) {
            return
        }
        if (bannerRunnable == null) {
            bannerRunnable = BannerRunnable()
        }
        postDelayed(bannerRunnable, period)
    }

    private fun stopPlay() {
        removeCallbacks(bannerRunnable)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        when (ev?.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                if (autoPlay) {
                    stopPlay()
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_OUTSIDE -> {
                if (autoPlay) {
                    startPlay()
                }
            }
            else -> {
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    class BannerAdapterWrapper(
        private val infinite: Boolean,
        private val adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>
    ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return adapter.onCreateViewHolder(parent, viewType)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            adapter.bindViewHolder(holder, position)
        }

        override fun getItemCount(): Int {
            return getItemCountInternal()
        }

        override fun getItemViewType(position: Int): Int {
            return adapter.getItemViewType(position)
        }

        fun getRealItemCount(): Int {
            return adapter.itemCount
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

        fun getRealPosition(position: Int): Int {
            var realPosition = 0
            if (getRealItemCount() > 1) {
                realPosition = (position - SIDE_COUNT) % getRealItemCount()
            }
            if (realPosition < 0) {
                realPosition += getRealItemCount()
            }
            return realPosition
        }
    }

    class OnPageChangeCallback(
        private val infinite: Boolean,
        private val viewPager: ViewPager2,
        private val adapter: BannerAdapterWrapper,
        private val indicatorLayout: ProgressIndicator? = null
    ) : ViewPager2.OnPageChangeCallback() {
        private var tempPosition: Int = 0
        override fun onPageSelected(position: Int) {
            if (adapter.getRealItemCount() > 1) {
                tempPosition = position
            }
            indicatorLayout?.setProgressCompat((adapter.getRealPosition(position) + 1) / adapter.getRealItemCount(), true)
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

    private inner class BannerRunnable : Runnable {
        override fun run() {
            var currentItem = binding.vpBanner.currentItem
            if (currentItem == homeBannerAdapter.getRealItemCount()) {
                currentItem = 0
                currentItem += 1
                binding.vpBanner.setCurrentItem(currentItem, false)
            } else {
                currentItem += 1
                binding.vpBanner.setCurrentItem(currentItem, true)
            }

            postDelayed(bannerRunnable, period)
        }
    }
}