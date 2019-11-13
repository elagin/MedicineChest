package ru.crew4dev.medicinechest.di.module.user

import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.crew4dev.medicinechest.data.network.mcb.credit.McbCreditApi
import ru.crew4dev.medicinechest.data.network.mcb.loan.McbLoanApi
import ru.crew4dev.medicinechest.data.network.user.UserApiFactory
import ru.crew4dev.medicinechest.data.repos.McbCreditDataRepo
import ru.crew4dev.medicinechest.di.scope.UserScope
import ru.crew4dev.medicinechest.domain.repos.ClientRepo
import ru.crew4dev.medicinechest.domain.repos.DocRepo
import ru.crew4dev.medicinechest.domain.repos.McbCreditRepo
import ru.crew4dev.medicinechest.domain.usecases.McbCreditUseCase

@Module
abstract class McbCreditModule {

    @Binds
    @UserScope
    abstract fun provideMcbCreditRepo(repo: McbCreditDataRepo): McbCreditRepo

    @Module
    companion object {

        @JvmStatic
        @Provides
        @UserScope
        fun provideMcbCreditApi(factory: UserApiFactory) = factory.create(McbCreditApi::class.java)

        @JvmStatic
        @Provides
        @UserScope
        fun provideMcbLoanApi(factory: UserApiFactory) = factory.create(McbLoanApi::class.java)

        @JvmStatic
        @Provides
        @UserScope
        fun provideMcbCreditUseCase(
            mcbCreditRepo: McbCreditRepo,
            clientRepo: ClientRepo,
            docRepo: DocRepo
        ): McbCreditUseCase {
            return McbCreditUseCase(mcbCreditRepo, clientRepo, docRepo)
        }
    }

}