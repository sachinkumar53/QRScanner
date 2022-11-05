package com.sachin.app.qrscanner.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sachin.app.qrscanner.db.ScanResult
import com.sachin.app.qrscanner.history.HistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val repository: HistoryRepository
) : ViewModel() {
    private var _resultLiveData = MutableLiveData("")
    val resultLiveData: LiveData<String?> = _resultLiveData

    fun onResult(result: ScanResult) {
        _resultLiveData.value = result.text
        repository.insert(result)
    }
}