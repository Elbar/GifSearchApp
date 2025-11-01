package com.sample.data.di

import com.sample.data.network.NetworkConnectivityObserver
import com.sample.data.remote.api.GiphyApiService
import com.sample.data.repository.GifRepositoryImpl
import com.sample.domain.network.ConnectivityObserver
import com.sample.domain.repository.GifRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single<Moshi> {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    single<HttpLoggingInterceptor> {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single<OkHttpClient> {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(GiphyApiService.BASE_URL)
            .client(get<OkHttpClient>())
            .addConverterFactory(MoshiConverterFactory.create(get<Moshi>()))
            .build()
    }

    single<GiphyApiService> {
        get<Retrofit>().create(GiphyApiService::class.java)
    }
}

val repositoryModule = module {
    single<GifRepository> {
        GifRepositoryImpl(get())
    }
    single<ConnectivityObserver> { NetworkConnectivityObserver(get()) }

}

val dataModules = listOf(repositoryModule, networkModule)

