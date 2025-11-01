package com.sample.domain.usecase

import com.sample.domain.model.Gif
import com.sample.domain.repository.GifRepository

class GetGifByIdUseCase(
    private val repository: GifRepository
) {
    suspend operator fun invoke(id: String): Result<Gif> {
        return repository.getGifById(id)
    }
}