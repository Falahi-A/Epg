package com.dazn.codeassignment.epg.domain.usecase

import com.dazn.codeassignment.epg.data.network.model.event.toEvent
import com.dazn.codeassignment.epg.domain.model.Event
import com.dazn.codeassignment.epg.domain.repository.EpgRepository
import com.dazn.codeassignment.epg.utils.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Named


class EventsUseCase @Inject constructor(
    private val repository: EpgRepository,
    @Named("IoDispatcher") private val ioDispatcher: CoroutineDispatcher
) {

    operator fun invoke() = flow<Resource<List<Event>>> {
        try {
            emit(Resource.Loading())
            val events = repository.getEvents()
            emit(Resource.Success(events.map {
                it.toEvent()
            }))
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