package br.com.listgistgithub.di

import br.com.listgistgithub.ui.favorite.viewmodel.FavoriteViewModel
import br.com.listgistgithub.ui.home.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel { HomeViewModel(get(), get()) }
    viewModel { FavoriteViewModel(get()) }
}