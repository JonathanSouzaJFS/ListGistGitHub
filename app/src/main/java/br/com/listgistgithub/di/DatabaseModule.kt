package br.com.listgistgithub.di

import androidx.room.Room
import br.com.listgistgithub.data.room.FavoriteDatabase
import br.com.listgistgithub.utils.DATABASE_NAME
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            FavoriteDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    single {
        get<FavoriteDatabase>().favoriteDAO()
    }
}