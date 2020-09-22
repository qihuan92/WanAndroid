package com.qihuan.wanandroid.biz.main

import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import com.qihuan.wanandroid.R
import com.qihuan.wanandroid.common.ktx.viewBinding
import com.qihuan.wanandroid.common.net.ApiResult
import com.qihuan.wanandroid.common.net.handleEvent
import com.qihuan.wanandroid.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContentView(binding.root)
        initShortcuts()
        bindEvent()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.apply {
            if (action == Intent.ACTION_VIEW) {
                val uri = data
                if (uri != null) {
                    findNavController(R.id.layout_content).navigate(uri)
                }
            }
        }
    }

    private fun initShortcuts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N_MR1) {
            return
        }
        val shortcutManager = getSystemService(ShortcutManager::class.java)
        val shortcutInfoList = listOf(
            searchShortcutInfo(),
            treeShortcutInfo(),
            navigationShortcutInfo(),
            qaShortcutInfo(),
        )

        shortcutManager.dynamicShortcuts = shortcutInfoList
    }

    @RequiresApi(Build.VERSION_CODES.N_MR1)
    private fun searchShortcutInfo() = ShortcutInfo.Builder(this, "search")
        .setShortLabel(getString(R.string.search))
        .setLongLabel(getString(R.string.search))
        .setIcon(Icon.createWithResource(this, R.mipmap.ic_shortcut_search))
        .setIntent(Intent(Intent.ACTION_VIEW, Uri.parse("open://wanandroid/search")))
        .build()

    @RequiresApi(Build.VERSION_CODES.N_MR1)
    private fun treeShortcutInfo() = ShortcutInfo.Builder(this, "tree")
        .setShortLabel(getString(R.string.system_tree))
        .setLongLabel(getString(R.string.system_tree))
        .setIcon(Icon.createWithResource(this, R.mipmap.ic_shortcut_tree))
        .setIntent(Intent(Intent.ACTION_VIEW, Uri.parse("open://wanandroid/tree")))
        .build()

    @RequiresApi(Build.VERSION_CODES.N_MR1)
    private fun navigationShortcutInfo() = ShortcutInfo.Builder(this, "navigation")
        .setShortLabel(getString(R.string.navigation))
        .setLongLabel(getString(R.string.navigation))
        .setIcon(Icon.createWithResource(this, R.mipmap.ic_shortcut_navigation))
        .setIntent(Intent(Intent.ACTION_VIEW, Uri.parse("open://wanandroid/navigation")))
        .build()

    @RequiresApi(Build.VERSION_CODES.N_MR1)
    private fun qaShortcutInfo() = ShortcutInfo.Builder(this, "qa")
        .setShortLabel(getString(R.string.qa))
        .setLongLabel(getString(R.string.qa))
        .setIcon(Icon.createWithResource(this, R.mipmap.ic_shortcut_qa))
        .setIntent(Intent(Intent.ACTION_VIEW, Uri.parse("open://wanandroid/qa")))
        .build()

    private fun bindEvent() {
        // todo 全局异常处理
        handleEvent(ApiResult.Error::class).observe(this, {
            Toast.makeText(this, it.error.message, Toast.LENGTH_SHORT).show()
        })
    }
}
