package com.common.base

import android.content.Context
import android.content.res.ColorStateList
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.common.R
import com.common.utils.ResourceUtil
import com.common.utils.SharedPreferenceStorage
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

abstract class MvvmBaseFragment<B : ViewDataBinding, VM : ViewModel> : Fragment() {

    var connectivityManager: ConnectivityManager? = null

    val navController: NavController by lazy {
        Navigation.findNavController(requireView())
    }

    fun NavController.safeNavigate(direction: NavDirections) {
        currentDestination?.getAction(direction.actionId)?.run { navigate(direction) }
    }

    lateinit var activityContext: Context

    lateinit var binding: B

    lateinit var viewModel: VM

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var preferenceStorage: SharedPreferenceStorage

    private var progressView: View? = null

    @get:LayoutRes
    abstract val layoutId: Int

    open fun initView() {}

    abstract fun observeViewModel()

    abstract fun getViewModelClass(): Class<VM>


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Initialize the connectivityManager variable
        connectivityManager = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        initView()
        observeViewModel()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activityContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return setAndBindContentView(inflater, container, layoutId)
    }

    /* Sets the content view, creates the binding and attaches the view to the view model */
    private fun setAndBindContentView(inflater: LayoutInflater, container: ViewGroup?, @LayoutRes layoutResID: Int): View {
        if (!this::binding.isInitialized) {
            binding = DataBindingUtil.inflate(inflater, layoutResID, container, false)
        }
        viewModel = viewModelFactory.create(getViewModelClass())

        binding.lifecycleOwner = this
        return binding.root
    }

    protected fun showSnackbarMessage(message: String, block: () -> Unit) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).setAction(null) {
            block()
        }.show()
    }

    fun showDialog(message: String) {
        AlertDialog.Builder(activityContext)
            .setMessage(message)
            .setPositiveButton(getString(R.string.ok), null)
            .create()
            .show()
    }

    fun showSnackBarMessage(isSuccess: Boolean = false, message: String, showAction: Boolean = false, actionText: String = "") {
        if (message.isEmpty()) {
            return
        }
        val snackbar: Snackbar = if (!showAction)
            Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
        else
            Snackbar.make(binding.root, message, 5000)
        val view: View = snackbar.view
        val textView = view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        if (isSuccess) {
            if (showAction)
                view.background = ContextCompat.getDrawable(activityContext, R.drawable.snackbar_background_gradient)
            else
                view.background = ContextCompat.getDrawable(activityContext, R.drawable.snackbar_background_green)
            textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_tick, 0, 0, 0)

        } else {
            if (showAction)
                view.background = ContextCompat.getDrawable(activityContext, R.drawable.snackbar_background_red_gradient)
            else
                view.background = ContextCompat.getDrawable(activityContext, R.drawable.snackbar_background_red)
            textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_warning, 0, 0, 0)
        }
        textView.setTextColor(ContextCompat.getColor(activityContext, R.color.white))

        if (showAction) {
            snackbar.setActionTextColor(ColorStateList.valueOf(ResourceUtil.getColorFromAttributeId(activityContext, R.attr.colorTextThird)))
            snackbar.setAction(actionText, {})
        }

        snackbar.addCallback(object : Snackbar.Callback() {
            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                //super.onDismissed(transientBottomBar, event)
            }
        })
        textView.compoundDrawablePadding = 10
        textView.textSize = 14f
        textView.maxLines = 4
        textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
        snackbar.show()
    }

    fun showProgressDialog() {
        if (progressView == null) {
            val rootLayout =activity?.findViewById<FrameLayout>(android.R.id.content)
            val inflater = LayoutInflater.from(activityContext)
            progressView = inflater.inflate(R.layout.progress_layout, null, true)
            progressView?.isEnabled = false
            rootLayout?.addView(progressView)
            progressView?.bringToFront()
        }
    }

    fun hideProgressDialog() {
        progressView?.let {
            progressView?.visibility = View.GONE
            progressView?.parent?.let {
                val vg = it as ViewGroup
                vg.removeView(progressView)
            }
            progressView = null
        }
    }

    fun showErrorDialog(exception: Exception) {
        hideProgressDialog()
        val defaultMessage = activityContext.getString(R.string.an_error_occurred)
        val errorMessage = exception.message ?: defaultMessage
        val errorClassName = exception.javaClass.simpleName
        val stackTraceElement = exception.stackTrace.firstOrNull()
        val errorLocation = stackTraceElement?.let {
            "${it.className}.${it.methodName}(${it.fileName}:${it.lineNumber})"
        } ?: "Un known Location"

        val fullErrorMessage = buildString {
            appendLine(errorMessage)
            appendLine()
            append(errorClassName)
            appendLine()
            append(errorLocation)
        }

        AlertDialog.Builder(activityContext)
            .setTitle("Error")
            .setMessage(fullErrorMessage)
            .setPositiveButton(activityContext.getString(R.string.ok), null)
            .create()
            .show()
    }
    override fun onDestroy() {
        super.onDestroy()
        // Release the connectivityManager reference
        connectivityManager = null
    }
}
