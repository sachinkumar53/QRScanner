package com.sachin.app.qrscanner.history

import com.sachin.app.qrscanner.db.AppDatabase
import com.sachin.app.qrscanner.db.ApplicationScope
import com.sachin.app.qrscanner.db.ScanResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HistoryRepository @Inject constructor(
    database: AppDatabase,
    @ApplicationScope
    val scope: CoroutineScope
) {

    private val dao = database.scanResultDao
    fun getAllResults() = dao.getAllResults()

    fun insert(result: ScanResult) {
        scope.launch { dao.insert(result) }
    }

    fun delete(result: ScanResult) {
        scope.launch { dao.delete(result) }
    }
}