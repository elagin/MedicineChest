package ru.crew4dev.medicinechest.di.module.user

import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.crew4dev.medicinechest.data.network.npf.ops.OpsApi
import ru.crew4dev.medicinechest.data.network.user.UserApiFactory
import ru.crew4dev.medicinechest.data.repos.OpsDataRepo
import ru.crew4dev.medicinechest.di.scope.UserScope
import ru.crew4dev.medicinechest.domain.repos.ClientRepo
import ru.crew4dev.medicinechest.domain.repos.DocRepo
import ru.crew4dev.medicinechest.domain.repos.OpsRepo
import ru.crew4dev.medicinechest.domain.usecases.OpsUseCase

@Module
abstract class OpsModule {

    @Binds
    @UserScope
    abstract fun provideOpsRepo(repo: OpsDataRepo): OpsRepo

    @Module
    companion object {

        @JvmStatic
        @Provides
        @UserScope
        fun provideOpsApi(factory: UserApiFactory) = factory.create(OpsApi::class.java)

        @JvmStatic
        @Provides
        @UserScope
        fun provideOpsUseCase(
            opsRepo: OpsRepo,
            clientRepo: ClientRepo,
            docRepo: DocRepo
        ) = OpsUseCase(opsRepo, clientRepo, docRepo)
    }

}