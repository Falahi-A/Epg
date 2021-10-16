package com.dazn.codeassignment.epg.domain.usecase

import com.dazn.codeassignment.epg.data.network.model.schedule.toSchedule
import com.dazn.codeassignment.epg.domain.model.Schedule
import com.dazn.codeassignment.epg.domain.repository.EpgRepository
import com.dazn.codeassignment.epg.utils.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Named

class SchedulesUseCase @Inject constructor(
    private val repository: EpgRepository,
    @Named("IoDispatcher") private val ioDispatcher: CoroutineDispatcher
) {

    operator fun invoke() = flow<Resource<List<Schedule>>> {
        try {
            emit(Resource.Loading())
            val schedules = repository.getSchedules()
            emit(Resource.Success(schedules.map { it.toSchedule() }))
        } catch (httpException: HttpException) {
            emit(
                Resource.Error(
                    message = httpException.localizedMessage ?: "an unexpected error occurred"
                )
            )
        } catch (ioException: IOException) {
            emit(
                Resource.Error(
                    message = ioException.localizedMessage
                        ?: "couldn't reach server, check your internet please"
                )
            )
        }


    }.flowOn(ioDispatcher)
}
