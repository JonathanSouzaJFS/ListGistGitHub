package br.com.testbasis.ui.details

import DetailsContract
import br.com.testbasis.data.model.Address
import br.com.testbasis.data.model.Person
import br.com.testbasis.data.repository.PersonRepository
import br.com.testbasis.ui.base.BasePresenter
import io.realm.RealmList
import org.koin.core.KoinComponent
import org.koin.core.inject


class DetailsPresenter(override val view: DetailsContract.View) :
    BasePresenter<DetailsContract.View>, DetailsContract.Presenter,
    KoinComponent {

    private val personRepository: PersonRepository by inject()
    private val addressList: RealmList<Address> = RealmList()

    override fun getAddressList(): RealmList<Address> {
        return addressList
    }

    override fun createPerson(person: Person) {
        try {
            personRepository.insertPerson(person)
            view.onCreatePersonResponse(person)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun editPerson(person: Person) {
        try {
            personRepository.update(person)
            view.onEditPersonResponse(person)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getPersonById(personId: String) {
        try {
            val person = personRepository.getPersonById(personId)
            addressList.addAll(person.address)
            view.onGetPersonResponse(person)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun createAddress(address: Address) {
        try {
            addressList.add(address)
            view.onCreateAddressResponse(address)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun removeAddress(address: Address) {
        try {
            addressList.remove(address)
            view.onRemoveAddressResponse(address)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
