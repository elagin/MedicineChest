package ru.crew4dev.medicinechest.di.component

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.crew4dev.medicinechest.app.App
import ru.crew4dev.medicinechest.app.navigation.NavHolderLifecycle
//import ru.crew4dev.medicinechest.app.push.FirebasePushService
import ru.crew4dev.medicinechest.di.module.application.ApplicationModule
import ru.crew4dev.medicinechest.di.module.application.NavigationModule
import ru.crew4dev.medicinechest.di.scope.ApplicationScope
import ru.crew4dev.medicinechest.presentation.screens.main.MainViewModel

@ApplicationScope
@Component(modules = [
    ApplicationModule::class,
    NavigationModule::class
])
interface ApplicationComponent {

    fun inject(app: App)

    fun inject(target: NavHolderLifecycle)
    //fun inject(pushService: FirebasePushService)

    fun mainViewModel(): MainViewModel

    //fun loginComponent(): LoginComponent.Builder
    fun userComponent(): UserComponent.Builder

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(appContext: Context): Builder

        fun build(): ApplicationComponent
    }
}