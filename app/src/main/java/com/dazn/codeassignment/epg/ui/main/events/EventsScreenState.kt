package com.dazn.codeassignment.epg.ui.main.events

import com.dazn.codeassignment.epg.domain.model.Event

data class EventsScreenState(
    var loading: Boolean = false,
    var eventsList: List<Event> = emptyList(),
    var error: String = ""
)