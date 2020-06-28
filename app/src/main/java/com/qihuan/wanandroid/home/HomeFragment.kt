package com.qihuan.wanandroid.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.qihuan.wanandroid.R
import com.qihuan.wanandroid.databinding.FragmentHomeBinding
import com.qihuan.wanandroid.main.TabContainer

/**
 * HomeFragment
 * @author qi
 * @since 2020/6/28
 */
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
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