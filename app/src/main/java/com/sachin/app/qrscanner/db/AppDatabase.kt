package com.sachin.app.qrscanner.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ScanResult::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract val scanResultDao: ScanResultDao

    companion object {
        private const val DB_NAME = "results.db"
        private var lock = Any()
        private var ourInstance: AppDatabase? = null

        operator fun invoke(context: Context): AppDatabase {
            return ourInstance ?: synchronized(lock) {
                Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    DB_NAME
                ).build().also {
                    ourInstance = it
                }
            }
        }
    }
}