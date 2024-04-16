package com.dicoding.asclepius.view.article

import androidx.lifecycle.ViewModel
import com.dicoding.asclepius.data.repositories.ArticleRepository

class ArticleViewModel(private val articleRepository: ArticleRepository) : ViewModel() {

    fun getArticle() = articleRepository.getArticle()

}