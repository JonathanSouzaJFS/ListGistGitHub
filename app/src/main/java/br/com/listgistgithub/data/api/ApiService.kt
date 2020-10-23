package br.com.listgistgithub.data.api

import br.com.listgistgithub.model.Gist
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("gists/public")
    suspend fun getGists(@Query("page") page: Int, @Query("per_page") perPage: Int): List<Gist>
}