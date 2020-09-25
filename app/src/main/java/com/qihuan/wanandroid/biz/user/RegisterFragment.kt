package com.qihuan.wanandroid.biz.user

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.qihuan.wanandroid.R
import com.qihuan.wanandroid.common.ktx.viewBinding
import com.qihuan.wanandroid.common.net.ApiResult
import com.qihuan.wanandroid.databinding.FragmentRegisterBinding

/**
 * RegisterFragment
 * @author qi
 * @since 2020/9/24
 */
class RegisterFragment : Fragment(R.layout.fragment_register) {
    private val binding by viewBinding(FragmentRegisterBinding::bind)
    private val viewModel by viewModels<RegisterViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        bindView()
    }

    private fun bindView() {
        viewModel.loginResult.observe(viewLifecycleOwner) {
            when (it) {
                is ApiResult.Success -> {
                    requireActivity().onBackPressed()
                }
                is ApiResult.Error -> {
                    Snackbar.make(binding.root, it.error.toString(), Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }
}