package com.qihuan.wanandroid.common.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2

/**
 * BannerLayout
 * @author qi
 * @since 2020/7/16
 */
class BannerLayout(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    ConstraintLayout(context, attrs, defStyleAttr) {

    private lateinit var viewPager: ViewPager2
    private lateinit var bannerAdapter: BannerAdapter

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
        viewPager = ViewPager2(context)
        viewPager.id = View.generateViewId()
        addView(viewPager)

        val set = ConstraintSet()
        set.clone(this)
        set.connect(viewPager.id, ConstraintSet.TOP, id, ConstraintSet.TOP)
        set.connect(viewPager.id, ConstraintSet.BOTTOM, id, ConstraintSet.BOTTOM)
        set.connect(viewPager.id, ConstraintSet.LEFT, id, ConstraintSet.LEFT)
        set.connect(viewPager.id, ConstraintSet.RIGHT, id, ConstraintSet.RIGHT)
        set.applyTo(this)
    }

    private fun setUpView() {
        bannerAdapter = BannerAdapter()
        viewPager.adapter = bannerAdapter

        bannerAdapter.itemCount
    }

    class BannerAdapter : RecyclerView.Adapter<BannerAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            TODO("Not yet implemented")
        }

        override fun getItemCount(): Int {
            TODO("Not yet implemented")
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            TODO("Not yet implemented")
        }

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        }
    }
}