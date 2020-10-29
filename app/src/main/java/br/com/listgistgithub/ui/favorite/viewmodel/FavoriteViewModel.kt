package br.com.listgistgithub.ui.favorite.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import br.com.listgistgithub.data.model.Favorite
import br.com.listgistgithub.data.model.toGist
import br.com.listgistgithub.data.repository.FavoriteRepository
import br.com.listgistgithub.model.Gist
import br.com.listgistgithub.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteViewModel(private val favoriteRepository: FavoriteRepository) : BaseViewModel() {

    var listFavorite: MutableLiveData<List<Favorite>> = MutableLiveData()

    fun deleteFavorite(context: Context, ownerId: String) {
        loading.value = true
        launch {
            try {
                favoriteRepository.deleteFavoriteById(context, ownerId)
                loading.value = false
            } catch (e: Exception) {
                e.printStackTrace()
                loading.value = false
            }
        }
    }

    fun getFavorites(context: Context) {
        loading.value = true
        launch {
            withContext(IO) {
                val favorites = favoriteRepository.getFavorites(context)
                listFavorite.postValue(favorites)
                loading.postValue(false)
            }
        }
    }

    fun convertFavoriteToGist(favorites: List<Favorite>): List<Gist> {
        return favorites.map { it.toGist() }
    }
}
