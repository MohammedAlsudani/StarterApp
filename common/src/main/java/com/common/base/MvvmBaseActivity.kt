package com.common.base

import android.content.Context
import android.content.DialogInterface
import android.content.res.ColorStateList
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.common.R
import com.common.utils.ResourceUtil
import com.common.utils.SharedPreferenceStorage
import com.google.android.material.snackbar.Snackbar
import java.util.Locale
import javax.inject.Inject


abstract class MvvmBaseActivity<B : ViewDataBinding, VM : ViewModel> : AppCompatActivity() {

    lateinit var binding: B

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var preferenceStorage: SharedPreferenceStorage

    lateinit var viewModel: VM

    @get:LayoutRes
    abstract val layoutId: Int

    abstract fun getViewModelClass(): Class<VM>

    var connectivityManager: ConnectivityManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setLocaleLang()
        setAppLocale(baseContext, getLocaleLang(baseContext))
        super.onCreate(savedInstanceState)
        connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        viewModel = viewModelFactory.create(getViewModelClass())
        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.lifecycleOwner = this
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        initView()
        observeViewModel()
        onBackPressedDispatcher.addCallback(this /* lifecycle owner */, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Back is pressed... Finishing the activity
                finish()
                overridePendingTransition(R.anim.no_animation , R.anim.slide_in_left)
            }
        })
    }



    open fun initView() {}

    abstract fun observeViewModel()

    fun showDialog(message: String, listener: DialogInterface.OnClickListener? = null) {
        if (!isFinishing) {
            AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton(getString(R.string.ok), listener)
                .setCancelable(false)
                .create()
                .show()
        }

    }

    fun showErrorDialog(message: String?) {
        showDialog(message ?: getString(R.string.an_error_occurred))
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
                view.background = ContextCompat.getDrawable(this@MvvmBaseActivity, R.drawable.snackbar_background_gradient)
            else
                view.background = ContextCompat.getDrawable(this@MvvmBaseActivity, R.drawable.snackbar_background_green)
            textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_tick, 0, 0, 0)

        } else {
            if (showAction)
                view.background = ContextCompat.getDrawable(this@MvvmBaseActivity, R.drawable.snackbar_background_red_gradient)
            else
                view.background = ContextCompat.getDrawable(this@MvvmBaseActivity, R.drawable.snackbar_background_red)
            textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_warning, 0, 0, 0)
        }
        textView.setTextColor(ContextCompat.getColor(this@MvvmBaseActivity, R.color.white))

        if (showAction) {
            snackbar.setActionTextColor(ColorStateList.valueOf(ResourceUtil.getColorFromAttributeId(this@MvvmBaseActivity, R.attr.colorTextThird)))
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

    override fun onDestroy() {
        super.onDestroy()
        // Release the connectivityManager reference
        connectivityManager = null
    }

    fun showToast(message: String) {
        // Display a toast message to the user
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


    private fun setLocaleLang() {
        val resources = baseContext.resources
        val configuration = resources.configuration
        val locale = getLocaleLang(baseContext)
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)
        getResources().updateConfiguration(configuration, getResources().displayMetrics)
    }

    private fun setAppLocale(context: Context, locale: Locale) {
        val config = context.resources.configuration
        config.setLocale(locale)
        Locale.setDefault(locale)
        context.createConfigurationContext(config)
    }

    open fun getLocaleLang(context: Context): Locale {
        val lang: String = SharedPreferenceStorage(context).localLanguage
        return Locale(lang)
    }
}