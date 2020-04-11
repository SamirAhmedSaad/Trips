package com.elabda3.tripstask.screens.tripsActivityPackage

import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akexorcist.googledirection.DirectionCallback
import com.akexorcist.googledirection.GoogleDirection
import com.akexorcist.googledirection.constant.AvoidType
import com.akexorcist.googledirection.model.Direction
import com.akexorcist.googledirection.model.Route
import com.elabda3.tripstask.base.NetworkException
import com.elabda3.tripstask.base.NetworkResult
import com.elabda3.tripstask.retrofitDataModel.TripData
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class TripsViewModelImp @Inject constructor() : ViewModel(), TripsViewModel {

    val progressLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val alertMessageLiveData: MutableLiveData<String> = MutableLiveData()
    val tripLoaded: MutableLiveData<TripData> = MutableLiveData()
    val routePoints: MutableLiveData<ArrayList<LatLng>> = MutableLiveData()

    @Inject
    lateinit var TripsNetworkImp: TripsNetworkImp

    @Inject
    lateinit var context: Context

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun getLatestTrip() {
        viewModelScope.launch {
            progressLiveData.postValue(true)

            when (val result = TripsNetworkImp.getLatestTrip()) {
                is NetworkResult.Success -> {
                    progressLiveData.postValue(false)
                    result.responseData?.data?.let {
                        tripLoaded.postValue(it)
                        handelCacheRoute(it)
                    }
                }
                is NetworkResult.Error -> {
                    progressLiveData.postValue(false)
                    if (!TextUtils.isEmpty(result.exception.message) && result.exception is NetworkException) {
                        alertMessageLiveData.postValue(result.exception.message)
                    }
                }
            }
        }
    }


    override fun getTrip(id: Int) {
        viewModelScope.launch {
            progressLiveData.postValue(true)

            when (val result = TripsNetworkImp.getTrip(id)) {
                is NetworkResult.Success -> {
                    progressLiveData.postValue(false)
                    result.responseData?.data?.let {
                        tripLoaded.postValue(it)
                        handelCacheRoute(it)
                    }
                }
                is NetworkResult.Error -> {
                    progressLiveData.postValue(false)
                    if (!TextUtils.isEmpty(result.exception.message) && result.exception is NetworkException) {
                        alertMessageLiveData.postValue(result.exception.message)
                    }
                }
            }
        }
    }

    override fun getRoute(
        tripId : Int,
        pickLat: Double,
        pickLng: Double,
        destinationLat: Double,
        destinationLng: Double,
        key: String
    ) {
        GoogleDirection.withServerKey(key)
            .from(LatLng(pickLat,pickLng))
            .to(LatLng(destinationLat,destinationLng))
            .avoid(AvoidType.FERRIES)
            .avoid(AvoidType.HIGHWAYS)
            .execute(object : DirectionCallback {
                override fun onDirectionSuccess(direction: Direction) {
                    if (direction.isOK) {
                        val route: Route = direction.routeList[0]
                        val leg = route.legList[0]
                        val pointList = leg.directionPoint
                        val stepList =
                            direction.routeList[0].legList[0].stepList

                        viewModelScope.launch(Dispatchers.IO) {
                            val pointsString = Gson().toJson(pointList)
                            sharedPreferences.edit().putString("$tripId",pointsString).apply()
                        }

                        routePoints.postValue(pointList)
                    }
                }
                override fun onDirectionFailure(t: Throwable) {

                }
            })
    }

    private fun handelCacheRoute(it: TripData) {
        if (it.fromCache) {
            val stepsJson = sharedPreferences.getString("${it.id}", "")
            if (!TextUtils.isEmpty(stepsJson)) {
                val gson = Gson()
                val type = object : TypeToken<List<LatLng?>?>() {}.type
                val stepsList: ArrayList<LatLng> = gson.fromJson(stepsJson, type)
                routePoints.value = stepsList
            }
        } else {
            getRoute(it.id, it.pickLat, it.pickLng, it.destinationLat, it.destinationLng)
        }
    }
}