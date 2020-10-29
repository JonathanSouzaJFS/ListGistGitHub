package br.com.listgistgithub.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.listgistgithub.data.model.Favorite

@Database(entities = [Favorite::class], version = 4, exportSchema = false)
abstract class FavoriteDatabase : RoomDatabase() {
    abstract fun favoriteDAO(): FavoriteDAO
}