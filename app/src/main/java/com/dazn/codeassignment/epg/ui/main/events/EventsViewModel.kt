package com.dazn.codeassignment.epg.ui.main.events

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dazn.codeassignment.epg.domain.usecase.EventsUseCase
import com.dazn.codeassignment.epg.utils.Resource
import com.dazn.codeassignment.epg.utils.convertDate
import com.dazn.codeassignment.epg.utils.getFakeEventsList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class EventsViewModel @Inject constructor(private val eventsUseCase: EventsUseCase) : ViewModel() {

    private val _eventsState = MutableLiveData<EventsScreenState>()
    val eventsState: LiveData<EventsScreenState> = _eventsState
    private val eventsScreenState = EventsScreenState(false, emptyList(), "")


    fun getEvents() =
        eventsUseCase().onEach { result ->
            when (result) {

                is Resource.Loading -> {
                    _eventsState.value=(eventsScreenState.apply {
                        loading = true
                        eventsList = emptyList()
                        error = ""
                    })
                }
                is Resource.Success -> {

                    _eventsState.value=(eventsScreenState.apply {
                        loading = false
                        result.data?.let { list ->
                            eventsList =
                                list.sortedWith(compareBy { event -> event.date }).map { event ->
                                    event.date = convertDate(event.date)
                                    event
                                }

                        }
                        error = ""
                    })
                }
                is Resource.Error -> {
                    _eventsState.value=(eventsScreenState.apply {
                        loading = false
                        eventsList = emptyList()
                        error = result.message ?: "an unexpected error occurred"
                    })
                }


            }
        }.launchIn(viewModelScope)


}