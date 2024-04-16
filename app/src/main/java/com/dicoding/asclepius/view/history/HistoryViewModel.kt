package com.dicoding.asclepius.view.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.asclepius.data.local.entity.HistoryEntity
import com.dicoding.asclepius.data.local.repository.HistoryRepository
import kotlinx.coroutines.launch

class HistoryViewModel(private val historyRepository: HistoryRepository) : ViewModel() {

    fun getHistory() = historyRepository.getHistory()

    fun delete(history: HistoryEntity) {
        viewModelScope.launch {
            historyRepository.delete(history)
        }
    }

    fun insert(history: HistoryEntity) {
        viewModelScope.launch {
            historyRepository.insert(history)
        }
    }
}