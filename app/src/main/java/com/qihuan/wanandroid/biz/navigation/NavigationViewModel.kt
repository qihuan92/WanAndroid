package com.qihuan.wanandroid.biz.navigation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qihuan.wanandroid.bean.TitleBean
import com.qihuan.wanandroid.common.adapter.DiffItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * NavigationViewModel
 * @author qi
 * @since 2020/9/16
 */
@HiltViewModel
class NavigationViewModel @Inject constructor(
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