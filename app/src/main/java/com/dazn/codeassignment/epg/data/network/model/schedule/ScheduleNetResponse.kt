package com.dazn.codeassignment.epg.data.network.model.schedule

import com.dazn.codeassignment.epg.domain.model.Schedule
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ScheduleNetResponse(
    @Json(name = "date")
    val date: String,
    @Json(name = "id")
    val id: String,
    @Json(name = "imageUrl")
    val imageUrl: String,
    @Json(name = "subtitle")
    val subtitle: String,
    @Json(name = "title")
    val title: String
)

fun ScheduleNetResponse.toSchedule(): Schedule {
    return Schedule(date, id, imageUrl, subtitle, title)
}