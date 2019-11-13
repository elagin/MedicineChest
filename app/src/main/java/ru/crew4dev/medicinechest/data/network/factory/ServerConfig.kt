package ru.crew4dev.medicinechest.data.network.factory

import java.net.URI

open class ServerConfig(val rootUrl: String, val pin: String? = null) {
    val apiUrl: String = URI("$rootUrl/api/v0/").normalize().toString()

    val host: String = URI(rootUrl).host
}