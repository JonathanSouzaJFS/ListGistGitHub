package br.com.testbasis.ui.home

import HomeContract
import br.com.testbasis.data.model.Person
import br.com.testbasis.data.repository.PersonRepository
import br.com.testbasis.ui.base.BasePresenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class HomePresenter(override val view: HomeContract.View) :
    BasePresenter<HomeContract.View>, HomeContract.Presenter,
    KoinComponent {

    private val scope = CoroutineScope(Job() + Dispatchers.Main)
    private val personRepository: PersonRepository by inject()

    override fun deletePerson(person: Person) {
        scope.launch {
            try {
                personRepository.deletePerson(person)
                view.onDeletePersonResponse(person)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun getPersonAll() {
        scope.launch {
            try {
                val personAll = personRepository.getPersonAll()
                view.onGetAllPersonResponse(personAll)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
