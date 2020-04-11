package com.elabda3.tripstask.base

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.elabda3.tripstask.R
import com.elabda3.tripstask.di.MyViewModelProviders
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import javax.inject.Inject


abstract class BaseActivity <T : ViewDataBinding, V : ViewModel> : DaggerAppCompatActivity() {

    @Inject
    lateinit var myViewModelProviders: MyViewModelProviders

    lateinit var dataBinding : T

    lateinit var viewModel : V

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    abstract fun resourceId(): Int

    abstract fun getViewModelClass(): Class<V>

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        dataBinding = DataBindingUtil.setContentView(this,resourceId())
        dataBinding.lifecycleOwner = this

        viewModel = ViewModelProvider(this,myViewModelProviders)[getViewModelClass()]


    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase ?: this))

    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.no_change, R.anim.exit_slide_right)
    }

}