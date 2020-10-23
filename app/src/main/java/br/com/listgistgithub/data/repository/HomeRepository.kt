package br.com.listgistgithub.data.repository

import br.com.listgistgithub.data.api.ApiHelper

class HomeRepository(private val apiHelper: ApiHelper) {
    suspend fun getGists(page: Int, perPage : Int) = apiHelper.getGists(page, perPage)
}