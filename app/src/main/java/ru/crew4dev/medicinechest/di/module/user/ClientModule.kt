package ru.crew4dev.medicinechest.di.module.user

import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.crew4dev.medicinechest.app.device.DeviceManager
import ru.crew4dev.medicinechest.data.network.client.ClientApi
import ru.crew4dev.medicinechest.data.network.document.DocApi
import ru.crew4dev.medicinechest.data.network.user.UserApiFactory
import ru.crew4dev.medicinechest.data.repos.ClientDataRepo
import ru.crew4dev.medicinechest.data.repos.DocDataRepo
import ru.crew4dev.medicinechest.di.scope.UserScope
import ru.crew4dev.medicinechest.domain.repos.ClientRepo
import ru.crew4dev.medicinechest.domain.repos.DocRepo
import ru.crew4dev.medicinechest.domain.usecases.CameraUseCase
import ru.crew4dev.medicinechest.domain.usecases.ClientUseCase

@Module
abstract class ClientModule {

    @Binds
    @UserScope
    abstract fun provideClientRepo(repo: ClientDataRepo): ClientRepo

    @Binds
    @UserScope
    abstract fun provideDocRepo(repo: DocDataRepo): DocRepo

    @Module
    companion object {

        @JvmStatic
        @Provides
        @UserScope
        fun provideClientApi(factory: UserApiFactory) = factory.create(ClientApi::class.java)

        @JvmStatic
        @Provides
        @UserScope
        fun provideDocApi(factory: UserApiFactory) = factory.create(DocApi::class.java)

        @JvmStatic
        @Provides
        @UserScope
        fun provideClientUseCase(clientRepo: ClientRepo, docRepo: DocRepo) = ClientUseCase(clientRepo, docRepo)

        @JvmStatic
        @Provides
        @UserScope
        fun provideCameraUseCase(
            docRepo: DocRepo,
            deviceManager: DeviceManager
        ) = CameraUseCase(docRepo, deviceManager)

    }

}