package br.com.listgistgithub.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.listgistgithub.data.api.ApiHelper
import br.com.listgistgithub.data.repository.HomeRepository
import br.com.listgistgithub.ui.home.viewmodel.HomeViewModel

class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(
                HomeRepository(apiHelper)
            ) as T
        }
        throw IllegalArgumentException("[ViewModel Error]: Unknown class name")
    }
}