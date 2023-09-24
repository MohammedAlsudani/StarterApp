package com.common.base

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.common.utils.SharedPreferenceStorage
import javax.inject.Inject

abstract class BaseDialogFragment<B : ViewDataBinding, VM : ViewModel> : DialogFragment() {

    lateinit var activityContext: Context

    protected lateinit var binding: B

    lateinit var viewModel: VM

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var preferenceStorage: SharedPreferenceStorage

    @get:LayoutRes
    abstract val layoutId: Int

    abstract val isFullSize: Boolean

    abstract fun getViewModelClass(): Class<VM>

    abstract fun observeViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, com.common.R.style.Theme_App)
    }

    override fun onCreateView(inflater: LayoutInflater, container : ViewGroup?, savedInstanceState: Bundle?): View {
        return setAndBindContentView(inflater, container, layoutId)
    }

    /* Sets the content view, creates the binding and attaches the view to the view model */
    private fun setAndBindContentView(inflater: LayoutInflater, container: ViewGroup?, @LayoutRes layoutResID: Int): View {
        viewModel = viewModelFactory.create(getViewModelClass())
        binding = DataBindingUtil.inflate(inflater, layoutResID, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(activityContext, com.common.R.style.Theme_App)
        if (isFullSize) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window?.setLayout(width,height)
        } else {
            val width = ViewGroup.LayoutParams.WRAP_CONTENT
            val height = ViewGroup.LayoutParams.WRAP_CONTENT
            dialog.window?.setLayout(width,height)
        }
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)
        dialog.window?.setGravity(Gravity.CENTER)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = androidx.appcompat.R.style.Animation_AppCompat_Dialog
        dialog.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                //This is the filter
                if (event.action != KeyEvent.ACTION_DOWN) true else {
                    true // pretend we've processed it
                }
            } else false // pass on to be processed as normal
        }
        return dialog
    }

    open fun initView() {}

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activityContext = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeViewModel()
    }
}