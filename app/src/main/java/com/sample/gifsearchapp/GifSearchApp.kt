package com.sample.gifsearchapp

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import com.sample.data.di.dataModules
import com.sample.domain.di.domainModule
import com.sample.presentation.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin

class GifSearchApp : Application(), KoinComponent, ImageLoaderFactory {
    private val imageLoader: ImageLoader by inject()

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@GifSearchApp)
            modules(domainModule + dataModules + presentationModule)
        }
    }
    override fun newImageLoader(): ImageLoader = imageLoader
}