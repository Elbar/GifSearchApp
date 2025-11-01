package com.sample.data.remote.api

import com.sample.data.remote.dto.GiphyResponse
import com.sample.data.remote.dto.GiphySingleResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GiphyApiService {

    @GET("v1/gifs/search")
    suspend fun searchGifs(
        @Query("api_key") apiKey: String,
        @Query("q") query: String,
        @Query("limit") limit: Int = 25,
        @Query("offset") offset: Int = 0,
        @Query("rating") rating: String = "g",
        @Query("lang") lang: String = "en"
    ): GiphyResponse


    @GET("v1/gifs/trending")
    suspend fun getTrendingGifs(
        @Query("api_key") apiKey: String,
        @Query("limit") limit: Int = 25,
        @Query("offset") offset: Int = 0,
        @Query("rating") rating: String = "g"
    ): GiphyResponse

    @GET("v1/gifs/{gif_id}")
    suspend fun getGifById(
        @Path("gif_id") gifId: String,
        @Query("api_key") apiKey: String
    ): GiphySingleResponse

    companion object {
        const val BASE_URL = "https://api.giphy.com/"
    }
}