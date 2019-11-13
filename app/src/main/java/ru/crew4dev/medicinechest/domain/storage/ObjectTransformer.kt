package ru.crew4dev.medicinechest.domain.storage


interface ObjectTransformer {

    fun toString(src: Any): String

    fun <T> fromString(src: String, classOfT: Class<T>): T
}