package ru.crew4dev.medicinechest.di.module.user

import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.crew4dev.medicinechest.data.network.vsk.VskApi
import ru.crew4dev.medicinechest.data.network.user.UserApiFactory
import ru.crew4dev.medicinechest.data.repos.VskDataRepo
import ru.crew4dev.medicinechest.di.scope.UserScope
import ru.crew4dev.medicinechest.domain.repos.VskRepo
import ru.crew4dev.medicinechest.domain.usecases.VskUseCase

@Module
abstract class VskModule {

    @Binds
    @UserScope
    abstract fun provideVskRepo(repo: VskDataRepo): VskRepo

    @Module
    companion object {

        @JvmStatic
        @Provides
        @UserScope
        fun provideVskApi(factory: UserApiFactory) = factory.create(VskApi::class.java)

        @JvmStatic
        @Provides
        @UserScope
        fun provideVskUseCase(repository: VskRepo) = VskUseCase(repository)
    }

}