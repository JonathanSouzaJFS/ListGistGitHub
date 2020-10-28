package br.com.listgistgithub.ui.favorite.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import br.com.listgistgithub.data.model.Favorite
import br.com.listgistgithub.data.model.toGist
import br.com.listgistgithub.data.repository.FavoriteRepository
import br.com.listgistgithub.model.Gist
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class FavoriteViewModel() : ViewModel() {

    var listFavorite: MutableLiveData<List<Favorite>> = MutableLiveData()

    private val scope = CoroutineScope(Job() + Dispatchers.IO)

    fun deleteFavorite(context: Context, ownerId: String) {
        try {
            scope.launch {
                FavoriteRepository.deleteFavoriteById(context, ownerId)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getFavorites(context: Context) {
        scope.launch {
            val favorites = FavoriteRepository.getFavorites(context)
            listFavorite.postValue(favorites)
        }
    }

    fun convertFavoriteToGist(favorites: List<Favorite>): List<Gist> {
        return favorites.map { it.toGist() }
    }
}
