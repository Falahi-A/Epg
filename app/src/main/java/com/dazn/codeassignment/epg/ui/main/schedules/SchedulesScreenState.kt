package com.dazn.codeassignment.epg.ui.main.schedules

import com.dazn.codeassignment.epg.domain.model.Schedule

data class SchedulesScreenState(
    var loading: Boolean = false,
    var schedulesList: List<Schedule> = emptyList(),
    var error: String = ""
)