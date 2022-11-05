package com.sachin.app.qrscanner.db

import android.util.Patterns
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ScanResult(
    val text: String,
    val date: Long = System.currentTimeMillis()
) {

    val isLink: Boolean
        get() = Patterns.WEB_URL.matcher(text).matches()

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}