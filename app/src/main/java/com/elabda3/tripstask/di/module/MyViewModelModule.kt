package com.elabda3.tripstask.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.elabda3.tripstask.di.MyViewModelProviders
import com.elabda3.tripstask.di.scope.MyMapKey
import com.elabda3.tripstask.screens.tripsActivityPackage.TripsViewModelImp
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class MyViewModelModule {

    @Binds
    @IntoMap
    @MyMapKey(TripsViewModelImp::class)
    abstract fun bindTripsViewModelImp(tripsViewModelImp : TripsViewModelImp) : ViewModel


    @Binds
    abstract fun bindViewModelFactory(factory: MyViewModelProviders): ViewModelProvider.Factory
}