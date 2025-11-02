package com.sample.data


import com.sample.data.remote.api.GiphyApiService
import com.sample.data.remote.dto.GifDto
import com.sample.data.remote.dto.GifImageDataDto
import com.sample.data.remote.dto.GifImagesDto
import com.sample.data.remote.dto.GiphySingleResponse
import com.sample.data.remote.dto.MetaDto
import com.sample.data.repository.GifRepositoryImpl
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GifRepositoryImplTest {

    private val apiService: GiphyApiService = mockk()
    private lateinit var repository: GifRepositoryImpl

    @Before
    fun setup() {
        repository = GifRepositoryImpl(apiService)
    }

    @Test
    fun `getGifById returns success when api call succeeds`() = runTest {
        // Given
        val gifId = "test-id"
        val mockGifDto = createMockGifDto()
        val mockResponse = GiphySingleResponse(
            data = mockGifDto,
            meta = MetaDto(200, "OK", "response-id")
        )
        coEvery { apiService.getGifById(gifId, any()) } returns mockResponse

        // When
        val result = repository.getGifById(gifId)

        // Then
        assertTrue(result.isSuccess)
        assertEquals(gifId, result.getOrNull()?.id)
    }

    @Test
    fun `getGifById returns failure when api call throws exception`() = runTest {
        // Given
        val gifId = "test-id"
        val exception = RuntimeException("Network error")
        coEvery { apiService.getGifById(gifId, any()) } throws exception

        // When
        val result = repository.getGifById(gifId)

        // Then
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is RuntimeException)
        assertEquals("Network error", result.exceptionOrNull()?.message)
    }

    @Test
    fun `getGifById maps DTO to domain model correctly`() = runTest {
        // Given
        val gifId = "test-123"
        val title = "Test Title"
        val username = "testuser"
        val mockGifDto = createMockGifDto().copy(
            id = gifId,
            title = title,
            username = username
        )
        val mockResponse = GiphySingleResponse(
            data = mockGifDto,
            meta = MetaDto(200, "OK", "response-id")
        )
        coEvery { apiService.getGifById(gifId, any()) } returns mockResponse

        // When
        val result = repository.getGifById(gifId)

        // Then
        val gif = result.getOrNull()
        assertEquals(gifId, gif?.id)
        assertEquals(title, gif?.title)
        assertEquals(username, gif?.username)
    }

    private fun createMockGifDto(): GifDto {
        val mockImageDataDto = GifImageDataDto(
            url = "https://example.com/image.gif",
            width = "480",
            height = "270",
            size = "1234567"
        )

        val mockImagesDto = GifImagesDto(
            original = mockImageDataDto,
            downsized = mockImageDataDto,
            downsizedMedium = mockImageDataDto,
            preview = mockImageDataDto,
            fixedHeight = mockImageDataDto,
            fixedWidth = mockImageDataDto
        )

        return GifDto(
            id = "test-id",
            title = "Test GIF",
            url = "https://giphy.com/gifs/test-id",
            username = "testuser",
            rating = "g",
            importDateTime = "2023-01-01 12:00:00",
            trendingDateTime = "2023-01-01 12:00:00",
            images = mockImagesDto
        )
    }
}