package com.qihuan.wanandroid.biz.home.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewBinder
import com.qihuan.wanandroid.bean.ModuleBean
import com.qihuan.wanandroid.common.ktx.buildIntent
import com.qihuan.wanandroid.common.ktx.load
import com.qihuan.wanandroid.common.ktx.openBrowser
import com.qihuan.wanandroid.databinding.ItemHomeModuleCellBinding

/**
 * ModuleViewDelegate
 * @author qi
 * @since 2020/8/13
 */
class ModuleCellItemViewBinder : ItemViewBinder<ModuleBean, ModuleCellItemViewBinder.ViewHolder>() {
    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        val binding = ItemHomeModuleCellBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, item: ModuleBean) {
        holder.bind(item)
    }

    class ViewHolder(
        private val binding: ItemHomeModuleCellBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ModuleBean) {
            binding.apply {
                ivBackground.load(item.backgroundImage)
                ivIcon.load(item.icon)
                tvTitle.text = item.title
                root.setOnClickListener {
                    if (item.link.isEmpty()) {
                        return@setOnClickListener
                    }
                    if (item.link.startsWith("http")) {
                        root.openBrowser(item.link)
                    } else {
                        root.context.startActivity(
                            buildIntent {
                                action = Intent.ACTION_VIEW
                                data = Uri.parse(item.link)
                            }
                        )
                    }
                }
            }
        }
    }
}