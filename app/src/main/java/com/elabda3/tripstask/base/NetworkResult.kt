package com.elabda3.tripstask.base

import retrofit2.Response

sealed class NetworkResult<out T:Any?> {

    data class Success<T: Any?>(val responseData : T?) : NetworkResult<T>()

    data class Error(val exception: Exception) : NetworkResult<Nothing>()

    data class NetworkOutCodes<T>(val response: Response<T>) : NetworkResult<T>()

}