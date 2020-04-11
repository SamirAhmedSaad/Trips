package com.elabda3.tripstask.screens.tripsActivityPackage

interface TripsViewModel {

    fun getLatestTrip()

    fun getTrip(id : Int)

    fun getRoute(tripId : Int,
                 pickLat : Double ,
                 pickLng : Double ,
                 destinationLat : Double,
                 destinationLng : Double,
                 key : String = "AIzaSyDyvTSn-ee4L8DTlUJS0AqXWAASC6HNBuI")
}