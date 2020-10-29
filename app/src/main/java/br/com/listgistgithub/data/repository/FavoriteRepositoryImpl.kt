package br.com.listgistgithub.data.repository

import android.content.Context
import br.com.listgistgithub.data.model.Favorite
import br.com.listgistgithub.data.room.FavoriteDAO
import br.com.listgistgithub.model.Gist
import br.com.listgistgithub.model.toFavorite
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class FavoriteRepositoryImpl(private val favoriteDatabase: FavoriteDAO) : FavoriteRepository {

    override fun insertFavorite(context: Context, gist: Gist) {
        CoroutineScope(IO).launch {
            val favorite = gist.toFavorite()
            favoriteDatabase.insertFavorite(favorite)
        }
    }

    override fun deleteFavoriteById(context: Context, ownerId: String) {
        CoroutineScope(IO).launch {
            favoriteDatabase.deleteFavoriteById(ownerId)
        }
    }

    override fun getFavoriteById(context: Context, ownerId: String): Favorite {
        return favoriteDatabase.getFavoriteById(ownerId)
    }

    override fun getFavorites(context: Context): MutableList<Favorite> {
        return favoriteDatabase.getAll()
    }

}