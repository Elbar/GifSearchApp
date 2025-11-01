package com.sample.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.domain.model.Gif
import com.sample.domain.usecase.GetGifByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val getGifByIdUseCase: GetGifByIdUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    fun loadGifDetails(gifId: String) {
        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
        
        viewModelScope.launch {
            getGifByIdUseCase(gifId)
                .onSuccess { gif ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        gif = gif,
                        errorMessage = null
                    )
                }
                .onFailure { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = exception.message ?: "An error occurred"
                    )
                }
        }
    }

    fun onRetry(gifId: String) {
        loadGifDetails(gifId)
    }
}


data class DetailUiState(
    val isLoading: Boolean = false,
    val gif: Gif? = null,
    val errorMessage: String? = null
)