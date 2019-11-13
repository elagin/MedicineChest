package ru.crew4dev.medicinechest.domain.storage.file

import java.io.File

interface FileStorage {
    fun getExternalStorageDirectory(): File
    fun getStorageDirectory(): File
    fun getFile(fileName: String): File
    fun deleteFile(fileName: String)
}