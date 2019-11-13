package ru.crew4dev.medicinechest.di.module.user

import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.crew4dev.medicinechest.data.network.npf.ipo.IpoApi
import ru.crew4dev.medicinechest.data.network.user.UserApiFactory
import ru.crew4dev.medicinechest.data.repos.IpoDataRepo
import ru.crew4dev.medicinechest.di.scope.UserScope
import ru.crew4dev.medicinechest.domain.repos.ClientRepo
import ru.crew4dev.medicinechest.domain.repos.DocRepo
import ru.crew4dev.medicinechest.domain.repos.IpoRepo
import ru.crew4dev.medicinechest.domain.usecases.IpoUseCase

@Module
abstract class IpoModule {

    @Binds
    @UserScope
    abstract fun provideIpoRepo(repo: IpoDataRepo): IpoRepo

    @Module
    companion object {

        @JvmStatic
        @Provides
        @UserScope
        fun provideIpoApi(factory: UserApiFactory) = factory.create(IpoApi::class.java)

        @JvmStatic
        @Provides
        @UserScope
        fun provideIpoUseCase(
            ipoRepo: IpoRepo,
            clientRepo: ClientRepo,
            docRepo: DocRepo
        ) = IpoUseCase(ipoRepo, clientRepo, docRepo)
    }

}