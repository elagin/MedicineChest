package ru.crew4dev.medicinechest.data.network.factory

import com.google.gson.Gson
import com.google.gson.GsonBuilder

object GsonFactory {

    val networkGson: Gson by lazy {
        GsonBuilder()
            .serializeNulls()
            .create()
    }

    val appGson: Gson by lazy {
        GsonBuilder()
            .serializeNulls()
            .create()
    }

}