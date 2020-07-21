package com.qihuan.wanandroid.biz.project

import androidx.fragment.app.Fragment
import com.qihuan.wanandroid.R
import com.qihuan.wanandroid.biz.main.TabContainer

/**
 * ProjectFragment
 * @author qi
 * @since 2020/6/28
 */
class ProjectFragment : Fragment() {
    class Tab : TabContainer {
        override fun title(): String {
            return "项目"
        }

        override fun icon(): Int {
            return R.drawable.ic_tab_project
        }

        override fun createFragment(): Fragment {
            return ProjectFragment()
        }
    }
}