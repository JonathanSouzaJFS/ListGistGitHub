package br.com.listgistgithub.data.repository

import android.content.Context
import br.com.listgistgithub.data.room.FavoriteDatabase
import br.com.listgistgithub.model.Favorite
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class FavoriteRepository {

    companion object {
        var favoriteDatabase: FavoriteDatabase? = null

        fun initializeDB(context: Context): FavoriteDatabase {
            return FavoriteDatabase.getDBClient(context)
        }

        fun insertFavorite(
            context: Context,
            ownerId: String,
            ownerName: String,
            ownerPhoto: String,
            ownerDescription: String
        ) {
            favoriteDatabase = initializeDB(context)
            CoroutineScope(IO).launch {
                val favorite = Favorite(ownerId, ownerName, ownerPhoto, ownerDescription)
                favoriteDatabase!!.favoriteDAO().insertFavorite(favorite)
            }
        }

        fun deleteFavoriteById(context: Context, ownerId: String) {
            favoriteDatabase = initializeDB(context)
            CoroutineScope(IO).launch {
                favoriteDatabase!!.favoriteDAO().deleteFavoriteById(ownerId)
            }
        }

        fun getFavoriteById(context: Context, ownerId: String): Favorite {
            favoriteDatabase = initializeDB(context)
            return favoriteDatabase!!.favoriteDAO().getFavoriteById(ownerId)
        }

        fun getFavorites(context: Context): MutableList<Favorite> {
            favoriteDatabase = initializeDB(context)
            return favoriteDatabase!!.favoriteDAO().getAll()
        }
    }
}