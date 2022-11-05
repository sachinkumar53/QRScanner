package com.sachin.app.qrscanner.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ScanResultDao {

    @Query("SELECT * FROM scanresult ORDER BY date DESC")
    fun getAllResults(): Flow<List<ScanResult>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(result: ScanResult)

    @Delete
    suspend fun delete(result: ScanResult)
}