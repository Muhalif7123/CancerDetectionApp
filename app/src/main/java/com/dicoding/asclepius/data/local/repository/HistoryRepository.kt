package com.dicoding.asclepius.data.local.repository

import androidx.lifecycle.LiveData
import com.dicoding.asclepius.data.local.dao.HistoryDao
import com.dicoding.asclepius.data.local.entity.HistoryEntity

class HistoryRepository(
    private val historyDao: HistoryDao
) {

    fun getHistory(): LiveData<List<HistoryEntity>> = historyDao.getHistory()

    suspend fun insert(history: HistoryEntity) {
        historyDao.insert(history)
    }

    suspend fun delete(history: HistoryEntity) {
        historyDao.delete(history)
    }

    companion object {
        @Volatile
        private var instance: HistoryRepository? = null
        fun getInstance(historyDao: HistoryDao): HistoryRepository =
            instance ?: synchronized(this) {
                instance ?: HistoryRepository(historyDao)

            }.also { instance = it }
    }
}