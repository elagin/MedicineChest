package ru.crew4dev.medicinechest.data.network.factory

import ru.crew4dev.medicinechest.BuildConfig

object ServerConfigFactory {

    private val config by lazy {
        ServerConfig(
            rootUrl = BuildConfig.SERVER_URL,
            pin = BuildConfig.SERVER_PIN
        )
    }

    fun getAppServerConfig() = config
}
