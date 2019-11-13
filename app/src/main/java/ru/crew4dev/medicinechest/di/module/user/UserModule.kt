package ru.crew4dev.medicinechest.di.module.user

import dagger.Module
import dagger.Provides
import ru.crew4dev.medicinechest.data.network.user.UserApiFactory
import ru.crew4dev.medicinechest.di.scope.UserScope
import ru.crew4dev.medicinechest.domain.providers.UserDataProvider

@Module
abstract class UserModule {

    @Module
    companion object {

        @JvmStatic
        @Provides
        @UserScope
        fun provideUserApiFactory(userDataProvider: UserDataProvider): UserApiFactory {
            return UserApiFactory(userDataProvider)
        }
    }
}