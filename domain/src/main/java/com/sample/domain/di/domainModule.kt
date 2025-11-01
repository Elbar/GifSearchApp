package com.sample.domain.di

import com.sample.domain.usecase.GetGifByIdUseCase
import com.sample.domain.usecase.GetTrendingGifsUseCase
import com.sample.domain.usecase.SearchGifsUseCase
import org.koin.dsl.module

val domainModule = module {
    single { GetGifByIdUseCase(get()) }
    single { GetTrendingGifsUseCase(get()) }
    single { SearchGifsUseCase(get()) }

    factory { SearchGifsUseCase(get()) }
    factory { GetTrendingGifsUseCase(get()) }
    factory { GetGifByIdUseCase(get()) }
}
