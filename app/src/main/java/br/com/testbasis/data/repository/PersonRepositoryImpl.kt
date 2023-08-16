package br.com.testbasis.data.repository

import br.com.testbasis.data.model.Person
import io.realm.Realm

class PersonRepositoryImpl : PersonRepository {

    private val db = Realm.getDefaultInstance()

    override fun insertPerson(person: Person) {
        db.executeTransactionAsync { transactionRealm ->
            transactionRealm.insert(person)
        }
    }

    override fun deletePerson(person: Person) {
        db.executeTransaction { transactionRealm ->
            transactionRealm.where(Person::class.java).equalTo("id", person.id).findFirst()
                ?.deleteFromRealm()
        }
    }

    override fun update(person: Person) {
        db.executeTransactionAsync { transactionRealm ->
            val tasks = transactionRealm.where(Person::class.java)
                .equalTo("id", person.id).findFirst()!!

            tasks.cnpj = person.cnpj
            tasks.isCompany = person.isCompany
            tasks.corporateName = person.corporateName
            tasks.cpf = person.cpf
            tasks.name = person.name
            tasks.email = person.email
            tasks.phone = person.phone
            // TODO ajuste bug
            // tasks.address = person.address
        }
    }

    override fun getPersonAll(): MutableList<Person> {
        return db.where(Person::class.java).findAll()
    }

    override fun getPersonById(personId: String): Person {
        return db.where(Person::class.java).equalTo("id", personId).findFirst()!!
    }
}