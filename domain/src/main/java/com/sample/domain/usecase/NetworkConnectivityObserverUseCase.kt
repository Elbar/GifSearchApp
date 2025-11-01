package com.sample.domain.usecase

import com.sample.domain.network.ConnectivityObserver
import com.sample.domain.network.NetworkStatus
import kotlinx.coroutines.flow.Flow

interface NetworkConnectivityObserverUseCase {
    fun observe(): Flow<NetworkStatus>
}

class NetworkConnectivityObserverUseCaseImpl(
    private val connectivityObserver: ConnectivityObserver
) : NetworkConnectivityObserverUseCase {

    override fun observe(): Flow<NetworkStatus> {
        return connectivityObserver.observe()
    }
}