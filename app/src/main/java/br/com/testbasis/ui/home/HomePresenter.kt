package br.com.testbasis.ui.home

import HomeContract
import br.com.testbasis.data.model.Person
import br.com.testbasis.data.repository.PersonRepository
import br.com.testbasis.ui.base.BasePresenter
import org.koin.core.KoinComponent
import org.koin.core.inject

class HomePresenter(override val view: HomeContract.View) :
    BasePresenter<HomeContract.View>, HomeContract.Presenter,
    KoinComponent {

    private val personRepository: PersonRepository by inject()

    override fun deletePerson(person: Person) {
        try {
            personRepository.deletePerson(person)
            view.onDeletePersonResponse(person)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getPersonAll() {
        try {
            val personAll = personRepository.getPersonAll()
            view.onGetAllPersonResponse(personAll)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
