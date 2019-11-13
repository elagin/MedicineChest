package ru.crew4dev.medicinechest.di.module.user

import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.crew4dev.medicinechest.data.network.statistics.StatisticsApi
import ru.crew4dev.medicinechest.data.network.user.UserApiFactory
import ru.crew4dev.medicinechest.data.repos.StatisticsDataRepo
import ru.crew4dev.medicinechest.di.scope.UserScope
import ru.crew4dev.medicinechest.domain.repos.StatisticsRepo
import ru.crew4dev.medicinechest.domain.usecases.StatisticsUseCase

@Module
abstract class StatisticsModule {

    @Binds
    @UserScope
    abstract fun provideStatisticsRepo(repo: StatisticsDataRepo): StatisticsRepo

    @Module
    companion object {

        @JvmStatic
        @Provides
        @UserScope
        fun provideStatisticsApi(factory: UserApiFactory) = factory.create(StatisticsApi::class.java)

        @JvmStatic
        @Provides
        @UserScope
        fun provideStatisticsUseCase(
            statisticsRepo: StatisticsRepo
        ): StatisticsUseCase {
            return StatisticsUseCase(statisticsRepo)
        }
    }

}