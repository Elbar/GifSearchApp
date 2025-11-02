package com.sample.presentation.di

import android.content.Context
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.util.DebugLogger
import com.sample.presentation.detail.DetailViewModel
import com.sample.presentation.search.SearchViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { SearchViewModel(get(), get(), get()) }
    viewModel { DetailViewModel(get()) }

    single<ImageLoader> {
        ImageLoader.Builder(get<Context>())
            .components {
                if (android.os.Build.VERSION.SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }
            .logger(DebugLogger())
            .respectCacheHeaders(false)
            .build()
    }
}
