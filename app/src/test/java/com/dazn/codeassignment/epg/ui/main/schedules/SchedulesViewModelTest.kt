package com.dazn.codeassignment.epg.ui.main.schedules

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dazn.codeassignment.epg.domain.model.Event
import com.dazn.codeassignment.epg.domain.model.Schedule
import com.dazn.codeassignment.epg.domain.repository.FakeEpgRepository
import com.dazn.codeassignment.epg.domain.usecase.SchedulesUseCase
import com.dazn.codeassignment.epg.domain.utils.CoroutinesTestRule
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule


@ExperimentalCoroutinesApi
class SchedulesViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule: CoroutinesTestRule = CoroutinesTestRule()

    private lateinit var fakeSchedulesUseCase: SchedulesUseCase
    private lateinit var fakeEpgRepository: FakeEpgRepository
    private lateinit var schedulesViewModel: SchedulesViewModel


    @Before
    fun setUp() {
        fakeEpgRepository = FakeEpgRepository()
        fakeSchedulesUseCase = SchedulesUseCase(fakeEpgRepository, testCoroutineRule.testDispatcher)
        schedulesViewModel = SchedulesViewModel(fakeSchedulesUseCase)
    }


    @Test
    fun `get schedules return schedules list`() = runBlockingTest {

        fakeEpgRepository.setSchedulesList(true)
        schedulesViewModel.getSchedules()
        Truth.assertThat(schedulesViewModel.schedules.value is SchedulesScreenState).isTrue()
        Truth.assertThat(schedulesViewModel.schedules.value?.schedulesList)
            .isEqualTo(fakeEpgRepository.getSchedulesList())

    }

    @Test
    fun `get schedules return empty list`() = runBlockingTest {

        schedulesViewModel.getSchedules()
        Truth.assertThat(schedulesViewModel.schedules.value is SchedulesScreenState).isTrue()
        Truth.assertThat(schedulesViewModel.schedules.value?.schedulesList)
            .isEqualTo(emptyList<Schedule>())

    }

}