package com.dicoding.asclepius.view.history

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.asclepius.data.local.repository.HistoryRepository
import com.dicoding.asclepius.di.Injection

class HistoryViewModelFactory(
    private val historyRepository: HistoryRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECK_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            return historyRepository?.let { HistoryViewModel(it) } as T
        }
        throw IllegalArgumentException("Unknown Viewmodel Class: ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var instance: HistoryViewModelFactory? = null
        fun getInstanceHistory(context: Context): HistoryViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: HistoryViewModelFactory(Injection.provideRepositoryHistory(context))
            }
    }
}