package com.qihuan.wanandroid.biz.navigation

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qihuan.wanandroid.bean.TitleBean
import com.qihuan.wanandroid.common.adapter.DiffItem
import kotlinx.coroutines.launch

/**
 * NavigationViewModel
 * @author qi
 * @since 2020/9/16
 */
class NavigationViewModel @ViewModelInject constructor(
    private val repository: NavigationRepository
) : ViewModel() {

    val navigationData by lazy { MutableLiveData<List<DiffItem>>() }

    init {
        getNavigation()
    }

    fun getNavigation() {
        viewModelScope.launch {
            val list = mutableListOf<DiffItem>()

            val navigation = repository.getNavigation()
            for (navigationBean in navigation) {
                list.add(TitleBean(navigationBean.name))
                list.addAll(navigationBean.articles)
            }

            navigationData.value = list
        }
    }
}