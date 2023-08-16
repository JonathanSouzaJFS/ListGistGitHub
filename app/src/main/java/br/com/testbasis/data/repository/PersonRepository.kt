package br.com.testbasis.data.repository

import br.com.testbasis.data.model.Person

interface PersonRepository {
    fun insertPerson(person: Person)
    fun deletePerson(person: Person)
    fun update(person: Person)
    fun getPersonAll(): MutableList<Person>
    fun getPersonById(personId: String): Person

}