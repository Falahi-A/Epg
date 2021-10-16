package com.dazn.codeassignment.epg.data.network

import com.dazn.codeassignment.epg.data.network.model.event.EventNetResponse
import com.dazn.codeassignment.epg.data.network.model.schedule.ScheduleNetResponse
import retrofit2.http.GET

interface EpgNetworkApi {

    @GET("getEvents")
  suspend  fun getEvents(): List<EventNetResponse>

    @GET("getSchedule")
    suspend fun getSchedules(): List<ScheduleNetResponse>

}