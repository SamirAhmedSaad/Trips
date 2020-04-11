package com.elabda3.tripstask.retrofitDataModel


import com.google.gson.annotations.SerializedName

data class TripData(
    @SerializedName("destinationLat")
    var destinationLat: Double,
    @SerializedName("destinationLng")
    var destinationLng: Double,
    @SerializedName("destinationName")
    var destinationName: String,
    @SerializedName("firstId")
    var firstId: Int,
    @SerializedName("id")
    var id: Int,
    @SerializedName("lastId")
    var lastId: Int,
    @SerializedName("nextId")
    var nextId: Int,
    @SerializedName("pickLat")
    var pickLat: Double,
    @SerializedName("pickLng")
    var pickLng: Double,
    @SerializedName("pickName")
    var pickName: String,
    @SerializedName("previousId")
    var previousId: Int,
    var fromCache : Boolean
)