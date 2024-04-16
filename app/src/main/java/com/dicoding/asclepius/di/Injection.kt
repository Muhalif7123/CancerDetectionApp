package com.dicoding.asclepius.di

import android.content.Context
import com.dicoding.asclepius.data.local.database.HistoryDatabase
import com.dicoding.asclepius.data.local.repository.HistoryRepository
import com.dicoding.asclepius.data.remote.retrofit.ApiConfig
import com.dicoding.asclepius.data.repositories.ArticleRepository

object Injection {

    fun provideRepositoryArticle(context: Context): ArticleRepository {
        val apiService = ApiConfig.getApiService()
        return ArticleRepository.getInstance(apiService)
    }

    fun provideRepositoryHistory(context: Context): HistoryRepository {
        val database = HistoryDatabase.getInstance(context)
        val dao = database.historyDao()
        return HistoryRepository.getInstance(dao)
    }
}