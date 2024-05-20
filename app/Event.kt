package com.aashay.spoonshare

import com.google.firebase.Timestamp
import com.google.firebase.firestore.GeoPoint

data class Event(
    val eventDescription: String,
    val eventGeopoint: GeoPoint,
    val eventHost: String,
    val eventImage: String,
    val eventName: String,
    val eventTime: Timestamp,
    val eventVolunteers: Long
)
