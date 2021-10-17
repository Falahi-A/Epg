package com.dazn.codeassignment.epg.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dazn.codeassignment.epg.domain.model.Event
import com.dazn.codeassignment.epg.domain.repository.FakeEpgRepository
import com.dazn.codeassignment.epg.domain.utils.CoroutinesTestRule
import com.dazn.codeassignment.epg.utils.Resource
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule


@ExperimentalCoroutinesApi
class EventsUseCaseTest {

    private lateinit var fakeRepository: FakeEpgRepository

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule: CoroutinesTestRule = CoroutinesTestRule()

    private lateinit var eventsUseCase: EventsUseCase


    @Before
    fun setup() {

        fakeRepository = FakeEpgRepository()
        eventsUseCase = EventsUseCase(fakeRepository, testCoroutineRule.testDispatcher)
    }


    @Test
    fun `invoke return events list`() = testCoroutineRule.testDispatcher.runBlockingTest {

        fakeRepository.setEventsList(true)
        fakeRepository.setSizeList(10)

        val events = eventsUseCase.invoke()

        Truth.assertThat(events.first() is Resource.Loading).isEqualTo(true)
        Truth.assertThat(events.drop(1).first() is Resource.Success).isEqualTo(true)
        Truth.assertThat(events.drop(1).first().data).isEqualTo(fakeRepository.getEventsList())
        Truth.assertThat(events.drop(1).first().data?.size).isEqualTo(10)


    }

    @Test
    fun `invoke return empty list`() = testCoroutineRule.testDispatcher.runBlockingTest {

        val events = eventsUseCase.invoke()

        Truth.assertThat(events.first() is Resource.Loading).isEqualTo(true)
        Truth.assertThat(events.drop(1).first() is Resource.Success).isEqualTo(true)
        Truth.assertThat(events.drop(1).first().data).isEqualTo(emptyList<Event>())

    }


    @Test
    fun `invoke throw an httpException`() = testCoroutineRule.testDispatcher.runBlockingTest {

        fakeRepository.setThrowHttpException(true)
        val events = eventsUseCase.invoke()

        Truth.assertThat(events.first() is Resource.Loading).isEqualTo(true)
        Truth.assertThat(events.drop(1).first() is Resource.Error).isEqualTo(true)

    }

    @Test
    fun `invoke throw an IOException`() = testCoroutineRule.testDispatcher.runBlockingTest {

        fakeRepository.setThrowIOException(true)
        val events = eventsUseCase.invoke()

        Truth.assertThat(events.first() is Resource.Loading).isEqualTo(true)
        Truth.assertThat(events.drop(1).first() is Resource.Error).isEqualTo(true)

    }

}