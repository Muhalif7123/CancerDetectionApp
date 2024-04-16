package com.dicoding.asclepius.data.remote.retrofit

import com.dicoding.asclepius.data.remote.response.ArticleResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("v2/top-headlines")
    suspend fun getNews(
        @Query("language") language: String,
        @Query("q?") q: String,
        @Query("category") category: String,
        @Query("pageSize") pageSize: Int,
        @Query("apiKey") apiKey: String
    ) : ArticleResponse
}