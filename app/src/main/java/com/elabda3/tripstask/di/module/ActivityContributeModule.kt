package com.elabda3.tripstask.di.module


import com.elabda3.tripstask.screens.tripsActivityPackage.TripsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityContributeModule {



    @ContributesAndroidInjector
    abstract fun getRepositoriesActivity(): TripsActivity

}