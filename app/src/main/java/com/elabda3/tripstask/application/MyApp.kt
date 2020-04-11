package com.elabda3.tripstask.application

import android.content.Context
import android.content.SharedPreferences
import com.elabda3.tripstask.R
import com.elabda3.tripstask.component.AppComponent
import com.elabda3.tripstask.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump
import javax.inject.Inject


class MyApp : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()

        setFont()

    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)

    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        var myComponent: AppComponent = DaggerAppComponent.builder().builder(this).build()
        myComponent.inject(this)
        return myComponent
    }

    private fun setFont() {
        ViewPump.init(ViewPump.builder()
            .addInterceptor( CalligraphyInterceptor(
                     CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/d_normal.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build())
            )
            .build())

    }
}