package br.com.listgistgithub.ui.home.viewmodel

import android.content.Context
import androidx.lifecycle.liveData
import br.com.listgistgithub.data.model.Favorite
import br.com.listgistgithub.data.repository.FavoriteRepository
import br.com.listgistgithub.data.repository.GistRepository
import br.com.listgistgithub.model.Gist
import br.com.listgistgithub.ui.base.BaseViewModel
import br.com.listgistgithub.utils.Resource
import br.com.listgistgithub.utils.hasInternet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(private val mainRepository: GistRepository) : BaseViewModel() {

    private var listFavorite: MutableList<Favorite> = mutableListOf()

    fun getGirts(context: Context, page: Int, perPage: Int = 30) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        if (hasInternet(context)) {
            try {
                emit(Resource.success(data = mainRepository.getGists(page, perPage)))
            } catch (exception: Exception) {
                emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
            }
        } else
            emit(Resource.error(data = null, message = "You have no internet connection!"))
    }

    fun insertFavorite(context: Context, gist: Gist) {
        launch {
            try {
                FavoriteRepository.insertFavorite(context, gist)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteFavorite(context: Context, ownerId: String) {
        launch {
            try {
                FavoriteRepository.deleteFavoriteById(context, ownerId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getFavorites(context: Context) {
        launch {
            withContext(IO) {
                val favorites = FavoriteRepository.getFavorites(context)
                listFavorite = favorites
            }
        }
    }

    fun setGistsFavorites(gists: List<Gist>) {
        listFavorite.forEach { favorite ->
            val gist = gists.find { it.id == favorite.ownerId }
            if (gist != null) gist.isFavorite = true
        }
    }
}
