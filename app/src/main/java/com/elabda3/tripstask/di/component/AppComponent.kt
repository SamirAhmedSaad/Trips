package com.elabda3.tripstask.component

import android.app.Application
import com.elabda3.tripstask.application.MyApp
import com.elabda3.tripstask.di.module.ApplicationModule
import com.elabda3.tripstask.di.module.MyViewModelModule
import com.elabda3.tripstask.di.module.NetworkModule
import com.elabda3.tripstask.di.module.SharedPrefrencesModule
import com.elabda3.tripstask.di.module.ActivityContributeModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, ApplicationModule::class, NetworkModule::class, SharedPrefrencesModule::class, ActivityContributeModule::class, MyViewModelModule::class])
interface AppComponent : AndroidInjector<MyApp> {


    @Component.Builder
    interface Builder{

        @BindsInstance
        fun builder(application: Application):Builder


        fun build():AppComponent

    }


    fun inject(application: Application)

}