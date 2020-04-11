package com.elabda3.tripstask.screens.tripsActivityPackage

import com.elabda3.tripstask.base.NetworkException
import com.elabda3.tripstask.base.NetworkResult
import com.elabda3.tripstask.base.safeCall
import com.elabda3.tripstask.retrofitApi.RetrofitApi
import com.elabda3.tripstask.retrofitDataModel.TripDataModel
import javax.inject.Inject


class TripsNetworkImp @Inject constructor() : TripsNetwork {

    @Inject
    lateinit var retrofitApi: RetrofitApi

    override suspend fun getLatestTrip(): NetworkResult<TripDataModel> = safeCall {

        val response = retrofitApi.getLatestTrip().await()

        if (response.isSuccessful) {
            if(response.body()?.status == 1) {
                if(response.raw().cacheResponse != null) {
                    response.body()?.data?.fromCache = true
                    return@safeCall NetworkResult.Success(response.body())
                }else{
                    response.body()?.data?.fromCache = false
                    return@safeCall NetworkResult.Success(response.body())
                }
            }else{
                return@safeCall NetworkResult.Error(NetworkException(response.body()?.message))
            }
        } else {
            return@safeCall NetworkResult.Error(NetworkException(""))
        }

    }

    override suspend fun getTrip(id: Int): NetworkResult<TripDataModel> = safeCall {

        val response = retrofitApi.getTrip(id).await()

        if (response.isSuccessful) {
            if(response.body()?.status == 1) {
                if(response.raw().cacheResponse != null) {
                    response.body()?.data?.fromCache = true
                    return@safeCall NetworkResult.Success(response.body())
                }else{
                    response.body()?.data?.fromCache = false
                    return@safeCall NetworkResult.Success(response.body())
                }
            }else{
                return@safeCall NetworkResult.Error(NetworkException(response.body()?.message))
            }
        } else {
            return@safeCall NetworkResult.Error(NetworkException(""))
        }

    }

}