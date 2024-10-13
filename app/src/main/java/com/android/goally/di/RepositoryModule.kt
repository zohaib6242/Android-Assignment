package com.getgoally.learnerapp.di

import android.app.Application
import com.android.goally.data.db.dao.*
import com.android.goally.data.network.rest.api.GeneralApi
import com.android.goally.data.repo.*
import com.android.goally.util.AppUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideGeneralRepo(
        authenticationApi: GeneralApi,
        authenticationDao: GeneralDao
    ): GeneralRepo {
        return GeneralRepo(
            authenticationApi,
            authenticationDao
        )
    }

    @Singleton
    @Provides
    fun provideCoPilotRepo(
        generalApi: GeneralApi,
        coPilotDao: CoPilotDao,
        application: Application
    ): CoPilotRepo {
        return CoPilotRepo(
            generalApi,
            coPilotDao,
            application
        )
    }

}
