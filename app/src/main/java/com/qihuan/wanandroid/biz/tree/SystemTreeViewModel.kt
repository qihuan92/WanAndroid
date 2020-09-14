package com.qihuan.wanandroid.biz.tree

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qihuan.wanandroid.bean.SystemNode
import kotlinx.coroutines.launch

/**
 * TreeViewModel
 * @author qi
 * @since 2020/9/14
 */
class SystemTreeViewModel @ViewModelInject constructor(
    private val repository: SystemTreeRepository
) : ViewModel() {

    val treeList by lazy { MutableLiveData<List<SystemNode>>(emptyList()) }

    init {
        getTreeList()
    }

    private fun getTreeList() {
        viewModelScope.launch {
            treeList.value = repository.getTreeList()
        }
    }
}