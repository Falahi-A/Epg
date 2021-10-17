package com.dazn.codeassignment.epg.domain.repository

import com.dazn.codeassignment.epg.data.network.model.event.EventNetResponse
import com.dazn.codeassignment.epg.data.network.model.event.toEvent
import com.dazn.codeassignment.epg.data.network.model.schedule.ScheduleNetResponse
import com.dazn.codeassignment.epg.data.network.model.schedule.toSchedule
import com.dazn.codeassignment.epg.domain.model.Event
import com.dazn.codeassignment.epg.domain.model.Schedule
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

/**
 *  Fake repository class
 */
class FakeEpgRepository : EpgRepository {

    private lateinit var eventsList: List<EventNetResponse>
    private lateinit var schedulesList: List<ScheduleNetResponse>
    private var hasSchedules = false // if it is true the Schedules list become filled
    private var hasEvents = false // if it is true the events list become filled
    private var hasHttpException = false // if it is true methods throw an httpException
    private var hasIOException = false // if it is true methods throw an IOException
    private var size: Int = 0 // size of lists

    fun setSizeList(size: Int) {
        this.size = size
    }


    fun setEventsList(hasEvents: Boolean) {
        this.hasEvents = hasEvents
    }

    fun setThrowHttpException(hasException: Boolean) {
        this.hasHttpException = hasException
    }

    fun setThrowIOException(hasException: Boolean) {
        this.hasIOException = hasException
    }

    fun setSchedulesList(hasSchedules: Boolean) {
        this.hasSchedules = hasSchedules
    }

    @Throws(HttpException::class)
    override suspend fun getEvents(): List<EventNetResponse> {
        return when {
            hasEvents -> createEventsList()
            hasHttpException -> throw HttpException(
                Response.error<Any>(
                    409, ResponseBody.create(
                        MediaType.parse("plain/text"), ""
                    )
                )
            )
            hasIOException -> throw IOException()
            else -> emptyList()
        }
    }

    @Throws(Exception::class)
    override suspend fun getSchedules(): List<ScheduleNetResponse> {
        return when {
            hasSchedules -> createSchedulesList()
            hasHttpException -> throw HttpException(
                Response.error<Any>(
                    409, ResponseBody.create(
                        MediaType.parse("plain/text"), ""
                    )
                )
            )
            hasIOException -> throw IOException()
            else -> emptyList()
        }
    }


    fun getEventsList(): List<Event> {
        return eventsList.map { eventNetResponse ->
            eventNetResponse.toEvent()
        }
    }

    fun getSchedulesList(): List<Schedule> {
        return schedulesList.map { scheduleNetResponse ->
            scheduleNetResponse.toSchedule()
        }
    }

    private fun createEventsList(): List<EventNetResponse> {
        eventsList = ArrayList()
        for (i: Int in 0 until size) {
            (eventsList as ArrayList<EventNetResponse>).add(
                EventNetResponse(
                    "",
                    i.toString(),
                    "",
                    "",
                    "",
                    ""
                )
            )
        }
        return eventsList

    }

    private fun createSchedulesList(): List<ScheduleNetResponse> {
        schedulesList = ArrayList()
        for (i: Int in 0 until size) {
            (schedulesList as ArrayList<ScheduleNetResponse>).add(
                ScheduleNetResponse(
                    "",
                    i.toString(),
                    "",
                    "",
                    ""
                )
            )
        }
        return schedulesList
    }
}