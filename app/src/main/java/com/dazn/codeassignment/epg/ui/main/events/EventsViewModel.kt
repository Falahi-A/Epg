package com.dazn.codeassignment.epg.ui.main.events

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dazn.codeassignment.epg.domain.usecase.EventsUseCase
import com.dazn.codeassignment.epg.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class EventsViewModel @Inject constructor(private val eventsUseCase: EventsUseCase) : ViewModel() {

    private val _eventsState = MutableLiveData<EventsScreenState>()
    val eventsState: LiveData<EventsScreenState> = _eventsState
    private val eventsScreenState = EventsScreenState(false, emptyList(), "")



    init {
        getEvents()
    }

     fun getEvents() {
         eventsUseCase().onEach { result ->
            when (result) {

                is Resource.Loading -> {
                    _eventsState.postValue(eventsScreenState.apply {
                        loading = true
                        eventsList = emptyList()
                        error = ""
                    })
                }
                is Resource.Success -> {
                    _eventsState.postValue(eventsScreenState.apply {
                        loading = false
                        result.data?.let {
                            eventsList = it
                        }
                        error = ""
                    })
                }
                is Resource.Error -> {
                    _eventsState.postValue(eventsScreenState.apply {
                        loading = false
                        eventsList = emptyList()
                        error = result.message ?: "an unexpected error occurred"
                    })
                }


            }
        }.launchIn(viewModelScope)

    }
}