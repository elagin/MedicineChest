package ru.crew4dev.medicinechest.di.module.user

import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.crew4dev.medicinechest.data.network.address.AddressApi
import ru.crew4dev.medicinechest.data.network.user.UserApiFactory
import ru.crew4dev.medicinechest.data.repos.AddressDataRepo
import ru.crew4dev.medicinechest.di.scope.UserScope
import ru.crew4dev.medicinechest.domain.repos.AddressRepo
import ru.crew4dev.medicinechest.domain.usecases.AddressUseCase

@Module
abstract class AddressModule {

    @Binds
    @UserScope
    abstract fun provideAddressRepo(repo: AddressDataRepo): AddressRepo

    @Module
    companion object {

        @JvmStatic
        @Provides
        @UserScope
        fun provideAddressApi(factory: UserApiFactory) = factory.create(AddressApi::class.java)

        @JvmStatic
        @Provides
        @UserScope
        fun provideClientUseCase(repo: AddressRepo) = AddressUseCase(repo)
    }

}