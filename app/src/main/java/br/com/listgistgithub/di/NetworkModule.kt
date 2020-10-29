package br.com.listgistgithub.di

import br.com.listgistgithub.data.api.ApiService
import br.com.listgistgithub.utils.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

val networkModule = module {

    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

    val builder = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .build()

    single<Retrofit> {
        Retrofit.Builder()
            .client(builder)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single {
        get<Retrofit>().create<ApiService>()
    }
}