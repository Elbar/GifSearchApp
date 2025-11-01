package com.sample.presentation.di

import com.sample.presentation.detail.DetailViewModel
import com.sample.presentation.search.SearchViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { SearchViewModel(get(), get(), get()) }
    viewModel { DetailViewModel(get()) }
}
