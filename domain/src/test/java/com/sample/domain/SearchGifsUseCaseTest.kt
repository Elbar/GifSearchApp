package com.sample.domain

import androidx.paging.PagingData
import com.sample.domain.model.Gif
import com.sample.domain.model.GifImageData
import com.sample.domain.model.GifImages
import com.sample.domain.repository.GifRepository
import com.sample.domain.usecase.SearchGifsUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class SearchGifsUseCaseTest {

    private val repository: GifRepository = mockk()
    private lateinit var searchGifsUseCase: SearchGifsUseCase

    @Before
    fun setup() {
        searchGifsUseCase = SearchGifsUseCase(repository)
    }

    @Test
    fun `when invoke called, then repository searchGifs is called with correct query`() = runTest {
        // Given
        val query = "funny cats"
        val mockPagingData = PagingData.from(listOf(createMockGif()))
        every { repository.searchGifs(query) } returns flowOf(mockPagingData)

        // When
        searchGifsUseCase(query)

        // Then
        verify { repository.searchGifs(query) }
    }

    @Test
    fun `when invoke called with empty query, then repository is still called`() = runTest {
        // Given
        val query = ""
        val mockPagingData = PagingData.from(emptyList<Gif>())
        every { repository.searchGifs(query) } returns flowOf(mockPagingData)

        // When
        searchGifsUseCase(query)

        // Then
        verify { repository.searchGifs(query) }
    }

    private fun createMockGif(): Gif {
        val mockImageData = GifImageData(
            url = "https://example.com/image.gif",
            width = "480",
            height = "270",
            size = "1234567",
            mp4 = null,
            mp4Size = null,
            webp = null,
            webpSize = null
        )

        val mockImages = GifImages(
            original = mockImageData,
            downsized = mockImageData,
            downsizedMedium = mockImageData,
            preview = mockImageData,
            fixedHeight = mockImageData,
            fixedWidth = mockImageData
        )

        return Gif(
            id = "test-id",
            title = "Test GIF",
            url = "https://giphy.com/gifs/test-id",
            previewUrl = "https://example.com/preview.gif",
            username = "testuser",
            rating = "g",
            importDateTime = "2023-01-01 12:00:00",
            trendingDateTime = "2023-01-01 12:00:00",
            images = mockImages
        )
    }
}