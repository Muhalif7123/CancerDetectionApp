package com.dicoding.asclepius.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dicoding.asclepius.BuildConfig
import com.dicoding.asclepius.data.remote.response.ArticlesItem
import com.dicoding.asclepius.data.remote.retrofit.ApiService
import com.dicoding.asclepius.helper.Result

class ArticleRepository(
    private val apiService: ApiService
) {

    fun getArticle(): LiveData<Result<List<ArticlesItem>>> = liveData {
        emit(Result.Loading)
        try {
            val client = apiService.getNews("en", "cancer", "health", 100, BuildConfig.TOKEN)
            if (client.status == "ok") {
                emit(Result.Success(client.articles.orEmpty().mapNotNull { it }))

            } else {
                Log.d("ArticleRepository", "error ${client.status.toString()}")
                emit(Result.Failure("Terjadi Kesalahan"))
            }
        } catch (e: Exception) {
            emit(Result.Failure(e.message.toString()))
        }
    }

    companion object {
        @Volatile
        private var instance: ArticleRepository? = null
        fun getInstance(apiService: ApiService): ArticleRepository =
            instance ?: synchronized(this) {
                instance ?: ArticleRepository(apiService)
        }.also { instance = it }
    }
}