package com.dazn.codeassignment.epg.data.network.model.event

import com.dazn.codeassignment.epg.domain.model.Event
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EventNetResponse(
    @Json(name = "date")
    val date: String,
    @Json(name = "id")
    val id: String,
    @Json(name = "imageUrl")
    val imageUrl: String,
    @Json(name = "subtitle")
    val subtitle: String,
    @Json(name = "title")
    val title: String,
    @Json(name = "videoUrl")
    val videoUrl: String
)

fun EventNetResponse.toEvent(): Event {
    return Event(date, id, imageUrl, subtitle, title, videoUrl)
}