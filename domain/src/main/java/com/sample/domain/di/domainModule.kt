package com.sample.domain.di

import android.content.Context
import com.sample.domain.usecase.GetGifByIdUseCase
import com.sample.domain.usecase.GetTrendingGifsUseCase
import com.sample.domain.usecase.NetworkConnectivityObserverUseCase
import com.sample.domain.usecase.NetworkConnectivityObserverUseCaseImpl
import com.sample.domain.usecase.SearchGifsUseCase
import org.koin.dsl.module

val domainModule = module {
    single { GetGifByIdUseCase(get()) }
    single { GetTrendingGifsUseCase(get()) }
    single { SearchGifsUseCase(get()) }

    single<NetworkConnectivityObserverUseCase> { NetworkConnectivityObserverUseCaseImpl(get()) }
}

