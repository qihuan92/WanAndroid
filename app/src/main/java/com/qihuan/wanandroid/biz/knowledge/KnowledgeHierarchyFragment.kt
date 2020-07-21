package com.qihuan.wanandroid.biz.knowledge

import androidx.fragment.app.Fragment
import com.qihuan.wanandroid.R
import com.qihuan.wanandroid.biz.main.TabContainer

/**
 * KnowledgeHierarchyFragment
 * @author qi
 * @since 2020/6/28
 */
class KnowledgeHierarchyFragment : Fragment() {

    class Tab : TabContainer {
        override fun title(): String {
            return "体系"
        }

        override fun icon(): Int {
            return R.drawable.ic_tab_knowledge_hierarchy
        }

        override fun createFragment(): Fragment {
            return KnowledgeHierarchyFragment()
        }
    }
}