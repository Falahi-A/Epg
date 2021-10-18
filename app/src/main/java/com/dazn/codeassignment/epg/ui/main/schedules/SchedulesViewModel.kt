package com.dazn.codeassignment.epg.ui.main.schedules

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dazn.codeassignment.epg.domain.usecase.SchedulesUseCase
import com.dazn.codeassignment.epg.utils.Resource
import com.dazn.codeassignment.epg.utils.convertDate
import com.dazn.codeassignment.epg.utils.getFakeEventsList
import com.dazn.codeassignment.epg.utils.getFakeSchedulesList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.logging.Handler
import javax.inject.Inject

@HiltViewModel
class SchedulesViewModel @Inject constructor(private val schedulesUseCase: SchedulesUseCase) :
    ViewModel() {

    private val _schedules = MutableLiveData<SchedulesScreenState>()

    val schedules: LiveData<SchedulesScreenState> = _schedules

    private val schedulesScreenState = SchedulesScreenState()


    fun getSchedules() {

        schedulesUseCase().onEach { result ->

            when (result) {

                is Resource.Loading -> {
                    _schedules.value = (schedulesScreenState.apply {
                        loading = true
                        schedulesList = emptyList()
                        error = ""
                    })
                }
                is Resource.Success -> {
                    _schedules.value = (schedulesScreenState.apply {
                        loading = false
                        result.data?.let { list ->
                            schedulesList = list.sortedWith(compareBy { schedule -> schedule.date })
                                .map { schedule ->
                                    schedule.date = convertDate(schedule.date)
                                    schedule
                                }
                        }
                        error = ""
                    })
                }
                is Resource.Error -> {
                    _schedules.value = (schedulesScreenState.apply {
                        loading = false
                        schedulesList = emptyList()
                        error = result.message ?: "an unexpected error occurred"
                    })
                }


            }

        }.launchIn(viewModelScope)

    }

}