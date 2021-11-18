package com.qihuan.wanandroid.biz.tree

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qihuan.wanandroid.bean.SystemNode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * TreeViewModel
 * @author qi
 * @since 2020/9/14
 */
@HiltViewModel
class SystemTreeViewModel @Inject constructor(
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