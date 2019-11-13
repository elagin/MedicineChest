package ru.crew4dev.medicinechest.di.module.user

import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.crew4dev.medicinechest.data.network.dvb.DvbApi
import ru.crew4dev.medicinechest.data.network.user.UserApiFactory
import ru.crew4dev.medicinechest.data.repos.DvbDataRepo
import ru.crew4dev.medicinechest.di.scope.UserScope
import ru.crew4dev.medicinechest.domain.repos.DocRepo
import ru.crew4dev.medicinechest.domain.repos.DvbRepo
import ru.crew4dev.medicinechest.domain.usecases.DvbUseCase

@Module
abstract class DvbModule {

    @Binds
    @UserScope
    abstract fun provideDvbRepo(repo: DvbDataRepo): DvbRepo

    @Module
    companion object {

        @JvmStatic
        @Provides
        @UserScope
        fun provideDvbApi(factory: UserApiFactory) = factory.create(DvbApi::class.java)

        @JvmStatic
        @Provides
        @UserScope
        fun provideDvbUseCase(
            docRepo: DocRepo,
            dvbRepo: DvbRepo
        ): DvbUseCase = DvbUseCase(docRepo, dvbRepo)
    }

}