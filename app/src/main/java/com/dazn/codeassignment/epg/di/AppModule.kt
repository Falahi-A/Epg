package com.dazn.codeassignment.epg.di

import com.dazn.codeassignment.epg.data.network.EpgNetworkApi
import com.dazn.codeassignment.epg.data.repository.EpgRepositoryImpl
import com.dazn.codeassignment.epg.domain.repository.EpgRepository
import com.dazn.codeassignment.epg.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideNetApi(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): EpgNetworkApi {
        return retrofit.create(EpgNetworkApi::class.java)
    }

    @Singleton
    @Provides
    @Named("IoDispatcher")
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Singleton
    @Provides
    @Named("MainDispatcher")
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @Singleton
    @Provides
    fun provideEpgRepository(api: EpgNetworkApi): EpgRepository {
        return EpgRepositoryImpl(api)
    }


}