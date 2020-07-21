package com.qihuan.wanandroid.biz.user

import androidx.fragment.app.Fragment
import com.qihuan.wanandroid.R
import com.qihuan.wanandroid.biz.main.TabContainer

/**
 * UserFragment
 * @author qi
 * @since 2020/6/28
 */
class UserFragment : Fragment() {
    class Tab : TabContainer {
        override fun title(): String {
            return "我的"
        }

        override fun icon(): Int {
            return R.drawable.ic_tab_user
        }

        override fun createFragment(): Fragment {
            return UserFragment()
        }
    }
}