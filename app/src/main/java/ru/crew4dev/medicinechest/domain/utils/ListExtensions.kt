package ru.crew4dev.medicinechest.domain.utils

fun List<Pair<String, Int>>.getKey(value: Int): String? {
    return firstOrNull { it.second == value }?.first
}