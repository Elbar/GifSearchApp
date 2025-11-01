package com.sample.presentation.extensions

fun String.formatFileSize(): String {
    return try {
        val size = this.toLong()
        when {
            size >= 1024 * 1024 -> String.format("%.1f MB", size / (1024.0 * 1024.0))
            size >= 1024 -> String.format("%.1f KB", size / 1024.0)
            else -> "$size B"
        }
    } catch (e: NumberFormatException) {
        this
    }
}