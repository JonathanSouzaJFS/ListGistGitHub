package br.com.listgistgithub.di

import androidx.room.Room
import br.com.listgistgithub.data.room.FavoriteDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            FavoriteDatabase::class.java,
            "LISTGIST_DATABASE"
        ).build()
    }

    single {
        get<FavoriteDatabase>().favoriteDAO()
    }
}