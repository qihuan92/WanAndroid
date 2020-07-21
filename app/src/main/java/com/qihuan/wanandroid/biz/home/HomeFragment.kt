package com.qihuan.wanandroid.biz.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.drakeet.multitype.MultiTypeAdapter
import com.qihuan.wanandroid.R
import com.qihuan.wanandroid.biz.main.TabContainer
import com.qihuan.wanandroid.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * HomeFragment
 * @author qi
 * @since 2020/6/28
 */
@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapter: MultiTypeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        initView()
        initData()
        initListener()
        return binding.root
    }

    private fun initView() {
        adapter = MultiTypeAdapter()
        adapter.register(HomeBannerViewDelegate())

        binding.rvList.layoutManager = LinearLayoutManager(context)
        binding.rvList.adapter = adapter

        binding.refreshLayout.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    private fun initData() {
        viewModel.refresh()
    }

    private fun initListener() {
        viewModel.listLiveData.observe(viewLifecycleOwner, Observer {
            adapter.items = it
            adapter.notifyDataSetChanged()
        })
    }

    class Tab : TabContainer {
        override fun title(): String {
            return "首页"
        }

        override fun icon(): Int {
            return R.drawable.ic_tab_home
        }

        override fun createFragment(): Fragment {
            return HomeFragment()
        }
    }
}