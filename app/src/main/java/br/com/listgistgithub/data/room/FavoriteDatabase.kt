package br.com.listgistgithub.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.listgistgithub.model.Favorite

@Database(entities = [Favorite::class], version = 1, exportSchema = false)
abstract class FavoriteDatabase : RoomDatabase() {

    abstract fun favoriteDAO(): FavoriteDAO

    companion object {

        @Volatile
        private var instance: FavoriteDatabase? = null

        // Singleton
        fun getDBClient(context: Context): FavoriteDatabase {
            if (instance != null) return instance!!
            synchronized(this) {
                instance = Room
                    .databaseBuilder(context, FavoriteDatabase::class.java, "LISTGIST_DATABASE")
                    .fallbackToDestructiveMigration()
                    .build()
                return instance!!
            }
        }
    }
}