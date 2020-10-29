package br.com.listgistgithub.data.repository

import br.com.listgistgithub.data.api.ApiService
import br.com.listgistgithub.model.Gist

class GistRepositoryImpl(private val apiService: ApiService) : GistRepository {
    override suspend fun getGists(page: Int, perPage: Int): List<Gist> {
        return apiService.getGists(page, perPage)
    }
}