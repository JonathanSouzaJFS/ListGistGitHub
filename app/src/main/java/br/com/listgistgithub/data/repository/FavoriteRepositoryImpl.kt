package br.com.listgistgithub.data.repository

import android.content.Context
import br.com.listgistgithub.data.model.Favorite
import br.com.listgistgithub.data.room.FavoriteDAO
import br.com.listgistgithub.model.Gist
import br.com.listgistgithub.model.toFavorite

class FavoriteRepositoryImpl(private val favoriteDatabase: FavoriteDAO) : FavoriteRepository {

    override suspend fun insertFavorite(context: Context, gist: Gist) {
        favoriteDatabase.insertFavorite(gist.toFavorite())
    }

    override suspend fun deleteFavoriteById(context: Context, ownerId: String) {
        favoriteDatabase.deleteFavoriteById(ownerId)
    }

    override suspend fun getFavoriteById(context: Context, ownerId: String): Favorite {
        return favoriteDatabase.getFavoriteById(ownerId)
    }

    override suspend fun getFavorites(context: Context): MutableList<Favorite> {
        return favoriteDatabase.getAll()
    }

}