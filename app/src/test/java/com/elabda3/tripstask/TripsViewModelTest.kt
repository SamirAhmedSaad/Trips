package com.elabda3.tripstask

import android.content.Context
import android.content.SharedPreferences
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.elabda3.tripstask.base.NetworkException
import com.elabda3.tripstask.base.NetworkResult
import com.elabda3.tripstask.retrofitDataModel.TripData
import com.elabda3.tripstask.retrofitDataModel.TripDataModel
import com.elabda3.tripstask.screens.tripsActivityPackage.TripsNetworkImp
import com.elabda3.tripstask.screens.tripsActivityPackage.TripsViewModelImp
import com.google.android.gms.maps.model.LatLng
import com.jraska.livedata.test
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

class TripsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    lateinit var tripsNetworkImp : TripsNetworkImp

    lateinit var  tripsViewModelImp: TripsViewModelImp

    @Before
    fun setup(){
        Dispatchers.setMain(mainThreadSurrogate)
        tripsNetworkImp  = mock(TripsNetworkImp::class.java)
        tripsViewModelImp = TripsViewModelImp(tripsNetworkImp)
    }


    @ExperimentalCoroutinesApi
    @Test
    fun `test alert should be trigger if no trips`() = runBlockingTest  {

        val data = NetworkException("no trips")

        whenever(tripsNetworkImp.getLatestTrip()).thenReturn(NetworkResult.Error(data))

        tripsViewModelImp.getLatestTrip()

        tripsViewModelImp.alertMessageLiveData.test().awaitValue().assertValue("no trips")

    }


    @ExperimentalCoroutinesApi
    @Test
    fun `test trips loaded`() = runBlockingTest  {

        val tripData = mock(TripData::class.java)
        val data = TripDataModel(200,tripData,"",1)

        whenever(tripsNetworkImp.getLatestTrip()).thenReturn(NetworkResult.Success(data))

        tripsViewModelImp.getLatestTrip()

        tripsViewModelImp.tripLoaded.test().awaitValue().assertValue(data.data)

    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }
}