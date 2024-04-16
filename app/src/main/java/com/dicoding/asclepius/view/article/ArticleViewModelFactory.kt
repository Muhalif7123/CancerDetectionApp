package com.dicoding.asclepius.view.article

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.asclepius.data.repositories.ArticleRepository
import com.dicoding.asclepius.di.Injection


class ArticleViewModelFactory(
    private val articleRepository: ArticleRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECK_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ArticleViewModel::class.java)) {
            return articleRepository?.let { ArticleViewModel(it) } as T
        }
        throw IllegalArgumentException("Unknown Viewmodel Class: ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var instance: ArticleViewModelFactory? = null
        fun getInstanceArticle(context: Context): ArticleViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ArticleViewModelFactory(Injection.provideRepositoryArticle(context))
            }.also { instance = it }
    }
}
