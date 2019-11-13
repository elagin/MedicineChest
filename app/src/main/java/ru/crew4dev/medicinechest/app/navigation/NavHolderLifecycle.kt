package ru.crew4dev.medicinechest.app.navigation

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import ru.crew4dev.medicinechest.di.DI
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import javax.inject.Inject

class NavHolderLifecycle(private val navigator: Navigator) : LifecycleObserver {

    @Inject
    lateinit var navigationHolder: NavigatorHolder

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        DI.app.inject(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        navigationHolder.setNavigator(navigator)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        navigationHolder.removeNavigator()
    }
}