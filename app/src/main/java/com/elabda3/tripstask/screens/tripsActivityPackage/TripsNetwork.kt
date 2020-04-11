package com.elabda3.tripstask.screens.tripsActivityPackage

import com.elabda3.tripstask.retrofitDataModel.TripDataModel
import com.elabda3.tripstask.base.NetworkResult

interface TripsNetwork {

    suspend fun getLatestTrip() : NetworkResult<TripDataModel>

    suspend fun getTrip(id : Int) : NetworkResult<TripDataModel>

}