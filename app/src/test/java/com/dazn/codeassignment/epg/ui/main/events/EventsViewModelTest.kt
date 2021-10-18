package com.dazn.codeassignment.epg.ui.main.events

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dazn.codeassignment.epg.domain.model.Event
import com.dazn.codeassignment.epg.domain.repository.FakeEpgRepository
import com.dazn.codeassignment.epg.domain.usecase.EventsUseCase
import com.dazn.codeassignment.epg.domain.utils.CoroutinesTestRule
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule


@ExperimentalCoroutinesApi
class EventsViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule: CoroutinesTestRule = CoroutinesTestRule()

    private lateinit var fakeEventsUseCase: EventsUseCase
    private lateinit var fakeEpgRepository: FakeEpgRepository
    private lateinit var eventsViewModel: EventsViewModel


    @Before
    fun setUp() {
        fakeEpgRepository = FakeEpgRepository()
        fakeEventsUseCase = EventsUseCase(fakeEpgRepository, testCoroutineRule.testDispatcher)
        eventsViewModel = EventsViewModel(fakeEventsUseCase)
    }


    @Test
    fun `get events return events list`() = runBlockingTest {

        fakeEpgRepository.setEventsList(true)
        eventsViewModel.getEvents()
        Truth.assertThat(eventsViewModel.eventsState.value is EventsScreenState).isTrue()
        Truth.assertThat(eventsViewModel.eventsState.value?.eventsList)
            .isEqualTo(fakeEpgRepository.getEventsList())

    }

    @Test
    fun `get events return empty list`() = runBlockingTest {

        eventsViewModel.getEvents()
        Truth.assertThat(eventsViewModel.eventsState.value is EventsScreenState).isTrue()
        Truth.assertThat(eventsViewModel.eventsState.value?.eventsList)
            .isEqualTo(emptyList<Event>())

    }

}