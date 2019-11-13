package ru.crew4dev.medicinechest.di

class ComponentHolder<out T>(
    private val constructor: () -> T,
    private val destructor: () -> Unit = {}
) {

    private var component: T? = null

    fun get(): T {
        val component = component
        return if (component == null) {
            val newComponent = constructor()
            this.component = newComponent
            newComponent
        } else {
            component
        }
    }

    fun destroy() {
        destructor.invoke()
        component = null
    }
}
