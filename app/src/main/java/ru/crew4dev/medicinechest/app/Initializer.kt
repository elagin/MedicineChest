package ru.crew4dev.medicinechest.app

import android.app.Application
import ru.crew4dev.medicinechest.di.DI

class Initializer(private val context: Application) {

    fun init() {
        buildDependencyGraph()
    }

    private fun buildDependencyGraph() = DI.init(context)

}