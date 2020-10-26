package br.com.listgistgithub.ui.home.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import br.com.listgistgithub.data.repository.FavoriteRepository
import br.com.listgistgithub.data.repository.HomeRepository
import br.com.listgistgithub.model.Favorite
import br.com.listgistgithub.model.Gist
import br.com.listgistgithub.utils.Resource
import kotlinx.coroutines.Dispatchers

class HomeViewModel(private val mainRepository: HomeRepository) : ViewModel() {

    var favorite: LiveData<Favorite>? = null

    fun getGirts(page: Int, perPage: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.getGists(page, perPage)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun insertFavorite(context: Context, gist: Gist) {
        FavoriteRepository.insertFavorite(
            context,
            gist.id,
            gist.owner!!.login!!,
            gist.owner!!.avatarUrl!!,
            gist.description!!
        )
    }
}
