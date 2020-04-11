package com.elabda3.tripstask.retrofitApi

import com.elabda3.tripstask.retrofitDataModel.TripDataModel
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*

interface RetrofitApi {

    @GET("trip/latest")
    fun getLatestTrip() : Deferred<Response<TripDataModel>>

    @GET("trip/{id}")
    fun getTrip(@Path("id") id : Int) : Deferred<Response<TripDataModel>>

}