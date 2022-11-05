package com.sachin.app.qrscanner.history

import androidx.lifecycle.ViewModel
import com.sachin.app.qrscanner.db.ScanResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class HistoryViewModel @Inject constructor(
    val repository: HistoryRepository
) : ViewModel() {

    val scanResultsFlow = repository.getAllResults()

    fun insert(result: ScanResult) = repository.insert(result)

    fun delete(result: ScanResult) = repository.delete(result)
}