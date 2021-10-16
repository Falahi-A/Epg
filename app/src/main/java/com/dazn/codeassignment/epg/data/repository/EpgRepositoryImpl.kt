package com.dazn.codeassignment.epg.data.repository

import com.dazn.codeassignment.epg.data.network.EpgNetworkApi
import com.dazn.codeassignment.epg.data.network.model.event.EventNetResponse
import com.dazn.codeassignment.epg.data.network.model.schedule.ScheduleNetResponse
import com.dazn.codeassignment.epg.domain.repository.EpgRepository
import javax.inject.Inject

class EpgRepositoryImpl @Inject constructor(private val epgNetworkApi: EpgNetworkApi) : EpgRepository {
    override suspend fun getEvents(): List<EventNetResponse> {
        return epgNetworkApi.getEvents()
    }

    override suspend fun getSchedules(): List<ScheduleNetResponse> {
        return epgNetworkApi.getSchedules()
    }
}