package com.elabda3.tripstask.di.module

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module(includes = [ApplicationModule::class])
class SharedPrefrencesModule {

    @Singleton
    @Provides
    fun getSharedPrefrences(context: Context): SharedPreferences {
        return context.getSharedPreferences("trips",MODE_PRIVATE)
    }


}