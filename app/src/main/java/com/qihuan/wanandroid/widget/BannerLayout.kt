package com.qihuan.wanandroid.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.progressindicator.ProgressIndicator
import com.qihuan.wanandroid.databinding.BannerLayoutBinding
import kotlin.math.abs


/**
 * BannerLayout
 *
 * @author qi
 * @since 2020/7/16
 */
class BannerLayout : ConstraintLayout, DefaultLifecycleObserver {

    companion object {
        const val INCREASE_COUNT = 2
        const val SIDE_COUNT = 1
    }

    private lateinit var binding: BannerLayoutBinding
    private lateinit var homeBannerAdapter: BannerAdapterWrapper<out RecyclerView.ViewHolder>

    private var infinite = true
    private var autoPlay = true
    private var period = 3000L
    private var bannerRunnable: BannerRunnable? = null

    private var startX = 0
    private var startY = 0

    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        initView()
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    ) {
        initView()
    }

    private fun initView() {
        binding = BannerLayoutBinding.inflate(LayoutInflater.from(context), this, true)
        val context = context
        if (context is FragmentActivity) {
            context.lifecycle.addObserver(this)
        }
    }

    fun setAdapter(adapter: RecyclerView.Adapter<out RecyclerView.ViewHolder>) {
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
                startX = ev.x.toInt()
                startY = ev.y.toInt()
                parent.requestDisallowInterceptTouchEvent(true)

                if (autoPlay) {
                    stopPlay()
                }
            }
            MotionEvent.ACTION_MOVE -> {
                val endX = ev.x.toInt()
                val endY = ev.y.toInt()
                val disX = abs(endX - startX)
                val disY = abs(endY - startY)
                if (disX > disY) {
                    parent.requestDisallowInterceptTouchEvent(true)
                }

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

    class BannerAdapterWrapper<VH : RecyclerView.ViewHolder>(
        private val infinite: Boolean,
        private val adapter: RecyclerView.Adapter<VH>
    ) : RecyclerView.Adapter<VH>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
            return adapter.onCreateViewHolder(parent, viewType)
        }

        override fun onBindViewHolder(holder: VH, position: Int) {
            adapter.bindViewHolder(holder, getRealPosition(position))
        }

        override fun onBindViewHolder(
            holder: VH,
            position: Int,
            payloads: MutableList<Any>
        ) {
            adapter.onBindViewHolder(holder, getRealPosition(position), payloads)
        }

        override fun getItemCount(): Int {
            return getItemCountInternal()
        }

        override fun getItemViewType(position: Int): Int {
            return adapter.getItemViewType(getRealPosition(position))
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
        private val adapter: BannerAdapterWrapper<out RecyclerView.ViewHolder>,
        private val indicatorLayout: ProgressIndicator? = null
    ) : ViewPager2.OnPageChangeCallback() {
        private var tempPosition: Int = 0
        override fun onPageSelected(position: Int) {
            val realItemCount = adapter.getRealItemCount()
            if (realItemCount > 1) {
                tempPosition = position
            }

            val realPosition = adapter.getRealPosition(position)
            val progress = ((realPosition + 1) * 100f / realItemCount).toInt()
            indicatorLayout?.setProgressCompat(progress, true)
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