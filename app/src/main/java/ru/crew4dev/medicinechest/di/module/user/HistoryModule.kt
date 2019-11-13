package ru.crew4dev.medicinechest.di.module.user

import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.crew4dev.medicinechest.data.repos.HistoryDataRepo
import ru.crew4dev.medicinechest.di.scope.UserScope
import ru.crew4dev.medicinechest.domain.repos.*
import ru.crew4dev.medicinechest.domain.usecases.HistoryDvbUseCase
import ru.crew4dev.medicinechest.domain.usecases.HistoryMcbUseCase
import ru.crew4dev.medicinechest.domain.usecases.HistoryUseCase

@Module
abstract class HistoryModule {

    @Binds
    @UserScope
    abstract fun provideHistoryRepo(repo: HistoryDataRepo): HistoryRepo

    @Module
    companion object {

        @JvmStatic
        @Provides
        @UserScope
        fun provideHistoryUseCase(
            historyRepo: HistoryRepo,
            historyMcbUseCase: HistoryMcbUseCase,
            historyDvbUseCase: HistoryDvbUseCase
        ): HistoryUseCase {
            return HistoryUseCase(historyRepo, historyMcbUseCase, historyDvbUseCase)
        }

        @JvmStatic
        @Provides
        @UserScope
        fun provideHistoryMcbUseCase(
            historyRepo: HistoryRepo,
            docRepo: DocRepo,
            mcbRepo: McbRepo,
            clientRepo: ClientRepo
        ): HistoryMcbUseCase {
            return HistoryMcbUseCase(historyRepo, docRepo, mcbRepo, clientRepo)
        }

        @JvmStatic
        @Provides
        @UserScope
        fun provideHistoryDvbUseCase(
            historyRepo: HistoryRepo,
            docRepo: DocRepo,
            dvbRepo: DvbRepo,
            clientRepo: ClientRepo
        ) = HistoryDvbUseCase(historyRepo, docRepo, dvbRepo, clientRepo)
    }

}