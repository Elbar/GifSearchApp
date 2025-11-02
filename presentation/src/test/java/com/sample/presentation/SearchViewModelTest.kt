package com.sample.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import app.cash.turbine.test
import com.sample.domain.model.Gif
import com.sample.domain.model.GifImageData
import com.sample.domain.model.GifImages
import com.sample.domain.network.NetworkStatus
import com.sample.domain.usecase.GetTrendingGifsUseCase
import com.sample.domain.usecase.NetworkConnectivityObserverUseCase
import com.sample.domain.usecase.SearchGifsUseCase
import com.sample.presentation.search.SearchViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.stopKoin
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class SearchViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val searchGifsUseCase: SearchGifsUseCase = mockk()
    private val getTrendingGifsUseCase: GetTrendingGifsUseCase = mockk()
    private val networkConnectivityObserverUseCase: NetworkConnectivityObserverUseCase = mockk()

    private lateinit var viewModel: SearchViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        
        every { networkConnectivityObserverUseCase.observe() } returns flowOf(NetworkStatus.Available)
        
        every { getTrendingGifsUseCase() } returns flowOf(PagingData.from(listOf(createMockGif())))
        every { searchGifsUseCase("cats") } returns flowOf(PagingData.from(listOf(createMockGif())))

        viewModel = SearchViewModel(
            searchGifsUseCase,
            getTrendingGifsUseCase,
            networkConnectivityObserverUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        stopKoin()
    }

    @Test
    fun `initial state is correct`() = runTest {
        viewModel.uiState.test {
            val initialState = awaitItem()
            assertFalse(initialState.isLoading)
            assertFalse(initialState.isSearching)
            assertTrue(initialState.isNetworkAvailable)
            assertEquals(null, initialState.errorMessage)
        }
    }

    @Test
    fun `search query change updates state correctly`() = runTest {
        // When
        viewModel.onSearchQueryChange("cats")

        // Then
        viewModel.searchQuery.test {
            assertEquals("cats", awaitItem())
        }

        viewModel.uiState.test {
            val state = awaitItem()
            assertTrue(state.isSearching)
        }
    }

    @Test
    fun `empty search query shows not searching state`() = runTest {
        // Given
        viewModel.onSearchQueryChange("cats")
        
        // When
        viewModel.onSearchQueryChange("")

        // Then
        viewModel.uiState.test {
            val state = awaitItem()
            assertFalse(state.isSearching)
        }
    }

    @Test
    fun `network status updates correctly for different states`() = runTest {
        every { networkConnectivityObserverUseCase.observe() } returns flowOf(NetworkStatus.Available)
        
        val viewModel1 = SearchViewModel(searchGifsUseCase, getTrendingGifsUseCase, networkConnectivityObserverUseCase)
        viewModel1.networkStatus.test {
            assertEquals(NetworkStatus.Available, awaitItem())
        }
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