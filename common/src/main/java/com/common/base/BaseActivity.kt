package com.common.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.common.utils.SharedPreferenceStorage
import javax.inject.Inject


abstract class BaseActivity<B : ViewDataBinding, VM : ViewModel> : AppCompatActivity() {

    private lateinit var binding: B

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var preferenceStorage: SharedPreferenceStorage

    lateinit var viewModel: VM

    @get:LayoutRes
    abstract val layoutId: Int

    abstract fun getViewModelClass(): Class<VM>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = viewModelFactory.create(getViewModelClass())
        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.lifecycleOwner = this
        initView()
        observeViewModel()
    }

    open fun initView() {}

    abstract fun observeViewModel()
}