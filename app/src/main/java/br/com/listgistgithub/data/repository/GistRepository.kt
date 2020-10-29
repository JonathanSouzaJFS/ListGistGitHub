package br.com.listgistgithub.data.repository

import br.com.listgistgithub.model.Gist

interface GistRepository {
    suspend fun getGists(page: Int, perPage: Int) : List<Gist>
}