package ru.crew4dev.medicinechest.app

import android.app.Application

class App : Application() {

    companion object {
        const val SMS_CODE_TIMEOUT_SECONDS = 30
        const val COMPRESSED_FILE_MAX_SIZE_KB = 200
        const val SEARCH_DELAY_MILLISECONDS = 1000L
    }

    override fun onCreate() {
        super.onCreate()
        Initializer(this).init()
    }

}