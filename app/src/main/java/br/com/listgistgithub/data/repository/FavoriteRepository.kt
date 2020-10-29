package br.com.listgistgithub.data.repository

import android.content.Context
import br.com.listgistgithub.data.model.Favorite
import br.com.listgistgithub.model.Gist

interface FavoriteRepository {
    fun insertFavorite(context: Context, gist: Gist)
    fun deleteFavoriteById(context: Context, ownerId: String)
    fun getFavoriteById(context: Context, ownerId: String): Favorite
    fun getFavorites(context: Context): MutableList<Favorite>
}