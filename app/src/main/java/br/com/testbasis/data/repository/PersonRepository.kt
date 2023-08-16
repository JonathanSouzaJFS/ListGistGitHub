package br.com.testbasis.data.repository

import br.com.testbasis.data.model.Person

interface PersonRepository {
    suspend fun insertPerson(person: Person)
    suspend fun deletePerson(person: Person)
    suspend fun update(person: Person)
    suspend fun getPersonAll(): MutableList<Person>
    suspend fun getPersonById(personId: String): Person

}