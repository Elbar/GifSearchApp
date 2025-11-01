package com.sample.domain.usecase

import androidx.paging.PagingData
import com.sample.domain.model.Gif
import com.sample.domain.repository.GifRepository
import kotlinx.coroutines.flow.Flow

class GetTrendingGifsUseCase(
    private val repository: GifRepository
) {
    operator fun invoke(): Flow<PagingData<Gif>> {
        return repository.getTrendingGifs()
    }
}