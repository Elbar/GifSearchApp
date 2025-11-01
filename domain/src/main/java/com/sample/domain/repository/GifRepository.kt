package com.sample.domain.repository

import androidx.paging.PagingData
import com.sample.domain.model.Gif
import kotlinx.coroutines.flow.Flow

interface GifRepository {
    fun searchGifs(query: String): Flow<PagingData<Gif>>
    fun getTrendingGifs(): Flow<PagingData<Gif>>
    suspend fun getGifById(id: String): Result<Gif>
}