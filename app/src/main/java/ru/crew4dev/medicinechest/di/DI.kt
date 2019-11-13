package ru.crew4dev.medicinechest.di

import android.content.Context
import ru.crew4dev.medicinechest.di.component.ApplicationComponent
import ru.crew4dev.medicinechest.di.component.DaggerApplicationComponent
import ru.crew4dev.medicinechest.di.component.UserComponent

object DI {

    lateinit var app: ApplicationComponent
    private lateinit var appContext: Context

//    val login: ComponentHolder<LoginComponent> = ComponentHolder(
//        constructor = { app.loginComponent().build() }
//    )
    val user: ComponentHolder<UserComponent> = ComponentHolder(
        constructor = { app.userComponent().build() }
    )

    fun getContext() = appContext

    fun init(context: Context) {
        this.app = DaggerApplicationComponent
            .builder()
            .context(context)
            .build()
        appContext = context
    }

}