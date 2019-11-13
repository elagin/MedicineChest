package ru.crew4dev.medicinechest.data.storage.file

import android.content.Context
import android.os.Environment
import ru.crew4dev.medicinechest.domain.exceptions.FileStorageNotAvailableException
import ru.crew4dev.medicinechest.domain.storage.file.FileStorage
import java.io.File
import javax.inject.Inject

class FileDataStorage @Inject constructor(private val appContext: Context) : FileStorage {
    override fun getExternalStorageDirectory(): File = Environment.getExternalStorageDirectory() ?: throw FileStorageNotAvailableException()

    override fun getStorageDirectory(): File = appContext.filesDir ?: throw FileStorageNotAvailableException()

    override fun getFile(fileName: String): File = File(getStorageDirectory(), fileName)

    override fun deleteFile(fileName: String) {
        getFile(fileName).delete()
    }
}