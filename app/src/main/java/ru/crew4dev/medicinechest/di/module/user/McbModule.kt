package ru.crew4dev.medicinechest.di.module.user

import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.crew4dev.medicinechest.data.network.mcb.debit.McbApi
import ru.crew4dev.medicinechest.data.network.user.UserApiFactory
import ru.crew4dev.medicinechest.data.repos.McbDataRepo
import ru.crew4dev.medicinechest.di.scope.UserScope
import ru.crew4dev.medicinechest.domain.repos.ClientRepo
import ru.crew4dev.medicinechest.domain.repos.DocRepo
import ru.crew4dev.medicinechest.domain.repos.McbRepo
import ru.crew4dev.medicinechest.domain.usecases.McbUseCase

@Module
abstract class McbModule {

    @Binds
    @UserScope
    abstract fun provideMcbRepo(repo: McbDataRepo): McbRepo

    @Module
    companion object {

        @JvmStatic
        @Provides
        @UserScope
        fun provideMcbApi(factory: UserApiFactory) = factory.create(McbApi::class.java)

        @JvmStatic
        @Provides
        @UserScope
        fun provideMcbUseCase(
            docRepo: DocRepo,
            mcbRepo: McbRepo,
            clientRepo: ClientRepo
        ): McbUseCase {

            return McbUseCase(docRepo, mcbRepo, clientRepo)
        }
    }

}