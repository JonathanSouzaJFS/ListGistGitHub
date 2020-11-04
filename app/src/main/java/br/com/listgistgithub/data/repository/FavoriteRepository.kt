package br.com.listgistgithub.data.repository

import android.content.Context
import br.com.listgistgithub.data.model.Favorite
import br.com.listgistgithub.model.Gist

interface FavoriteRepository {
    suspend fun insertFavorite(context: Context, gist: Gist)
    suspend fun deleteFavoriteById(context: Context, ownerId: String)
    suspend fun getFavoriteById(context: Context, ownerId: String): Favorite
    suspend fun getFavorites(context: Context): MutableList<Favorite>
}