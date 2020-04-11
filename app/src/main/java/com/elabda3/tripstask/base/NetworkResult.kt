package com.elabda3.tripstask.base

sealed class NetworkResult<out T:Any?> {

    data class Success<T: Any?>(val responseData : T?) : NetworkResult<T>()

    data class Error(val exception: Exception) : NetworkResult<Nothing>()
}