package com.rmakiyama.locationsample.model

data class Location(
    val latitude: Latitude,
    val longitude: Longitude
) {

    constructor(
        latitude: Double,
        longitude: Double
    ) : this(Latitude(latitude), Longitude(longitude))

    data class Latitude(val value: Double) {
        init {
            require(value in 0.0..90.0)
        }
    }

    data class Longitude(val value: Double) {
        init {
            require(value in 0.0..180.0)
        }
    }
}
