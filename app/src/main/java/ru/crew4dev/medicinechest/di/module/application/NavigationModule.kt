package ru.crew4dev.medicinechest.di.module.application

import dagger.Module
import dagger.Provides
import ru.crew4dev.medicinechest.di.scope.ApplicationScope
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router

@Module
abstract class NavigationModule {

    @Module
    companion object {

        @Provides
        @JvmStatic
        @ApplicationScope
        fun provideCicerone(): Cicerone<Router> = Cicerone.create()

        @Provides
        @JvmStatic
        @ApplicationScope
        fun provideNavHolder(cicerone: Cicerone<Router>): NavigatorHolder = cicerone.navigatorHolder

        @Provides
        @JvmStatic
        @ApplicationScope
        fun provideRouter(cicerone: Cicerone<Router>): Router = cicerone.router

    }
}