package com.sample.gifsearchapp

import android.app.Application
import com.sample.data.di.dataModule
import com.sample.domain.di.domainModule
import com.sample.presentation.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin

class GifSearchApp : Application(), KoinComponent {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()

            androidContext(this@GifSearchApp)
            modules(
                listOf(
                    domainModule,
                    dataModule,
                    presentationModule
                )
            )
        }
    }
}