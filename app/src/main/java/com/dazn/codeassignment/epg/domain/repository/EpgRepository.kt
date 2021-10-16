package com.dazn.codeassignment.epg.domain.repository

import com.dazn.codeassignment.epg.data.network.model.event.EventNetResponse
import com.dazn.codeassignment.epg.data.network.model.schedule.ScheduleNetResponse


interface EpgRepository {

   suspend fun getEvents(): List<EventNetResponse>

    suspend fun getSchedules(): List<ScheduleNetResponse>
}