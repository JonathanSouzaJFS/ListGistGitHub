package br.com.listgistgithub.data.api

class ApiHelper(private val apiService: ApiService) {
    suspend fun getGists(page: Int, perPage : Int) = apiService.getGists(page, perPage)
}