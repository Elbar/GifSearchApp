package com.sample.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sample.data.BuildConfig
import com.sample.data.mapper.toDomainModel
import com.sample.data.paging.SearchGifsPagingSource
import com.sample.data.paging.TrendingGifsPagingSource
import com.sample.data.remote.api.GiphyApiService
import com.sample.domain.model.Gif
import com.sample.domain.repository.GifRepository
import kotlinx.coroutines.flow.Flow

class GifRepositoryImpl(
    private val api: GiphyApiService
) : GifRepository {

    private val apiKey = BuildConfig.GIPHY_API_KEY

    override fun searchGifs(query: String): Flow<PagingData<Gif>> {
        return Pager(
            config = PagingConfig(
                pageSize = 25,
                enablePlaceholders = false,
                prefetchDistance = 5
            ),
            pagingSourceFactory = {
                SearchGifsPagingSource(api, apiKey, query)
            }
        ).flow
    }

    override fun getTrendingGifs(): Flow<PagingData<Gif>> {
        return Pager(
            config = PagingConfig(
                pageSize = 25,
                enablePlaceholders = false,
                prefetchDistance = 5
            ),
            pagingSourceFactory = {
                TrendingGifsPagingSource(api, apiKey)
            }
        ).flow
    }

    override suspend fun getGifById(id: String): Result<Gif> {
        return try {
            val response = api.getGifById(id, apiKey)
            Result.success(response.data.toDomainModel())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}