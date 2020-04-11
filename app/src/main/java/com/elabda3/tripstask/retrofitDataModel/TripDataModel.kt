package com.elabda3.tripstask.retrofitDataModel


import com.google.gson.annotations.SerializedName

data class TripDataModel(
    @SerializedName("Code")
    var code: Int,
    @SerializedName("Data")
    var `data`: TripData,
    @SerializedName("Message")
    var message: String,
    @SerializedName("Status")
    var status: Int
)