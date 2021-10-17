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
class SchedulesUseCaseTest {

    private lateinit var fakeRepository: FakeEpgRepository

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule: CoroutinesTestRule = CoroutinesTestRule()

    private lateinit var schedulesUseCase: SchedulesUseCase


    @Before
    fun setup() {

        fakeRepository = FakeEpgRepository()
        schedulesUseCase = SchedulesUseCase(fakeRepository, testCoroutineRule.testDispatcher)
    }


    @Test
    fun `invoke return schedules list`() = testCoroutineRule.testDispatcher.runBlockingTest {

        fakeRepository.setSchedulesList(true)
        fakeRepository.setSizeList(10)

        val schedules = schedulesUseCase.invoke()

        Truth.assertThat(schedules.first() is Resource.Loading).isEqualTo(true)
        Truth.assertThat(schedules.drop(1).first() is Resource.Success).isEqualTo(true)
        Truth.assertThat(schedules.drop(1).first().data).isEqualTo(fakeRepository.getSchedulesList())
        Truth.assertThat(schedules.drop(1).first().data?.size).isEqualTo(10)


    }

    @Test
    fun `invoke return empty list`() = testCoroutineRule.testDispatcher.runBlockingTest {

        val schedules = schedulesUseCase.invoke()

        Truth.assertThat(schedules.first() is Resource.Loading).isEqualTo(true)
        Truth.assertThat(schedules.drop(1).first() is Resource.Success).isEqualTo(true)
        Truth.assertThat(schedules.drop(1).first().data).isEqualTo(emptyList<Event>())

    }


    @Test
    fun `invoke throw an httpException`() = testCoroutineRule.testDispatcher.runBlockingTest {

        fakeRepository.setThrowHttpException(true)
        val schedules = schedulesUseCase.invoke()

        Truth.assertThat(schedules.first() is Resource.Loading).isEqualTo(true)
        Truth.assertThat(schedules.drop(1).first() is Resource.Error).isEqualTo(true)

    }

    @Test
    fun `invoke throw an IOException`() = testCoroutineRule.testDispatcher.runBlockingTest {

        fakeRepository.setThrowIOException(true)
        val schedules = schedulesUseCase.invoke()

        Truth.assertThat(schedules.first() is Resource.Loading).isEqualTo(true)
        Truth.assertThat(schedules.drop(1).first() is Resource.Error).isEqualTo(true)

    }

}