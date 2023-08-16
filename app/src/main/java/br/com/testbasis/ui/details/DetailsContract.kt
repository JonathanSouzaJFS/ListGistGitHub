import br.com.testbasis.data.model.Address
import br.com.testbasis.data.model.Person
import br.com.testbasis.ui.base.BasePresenter
import br.com.testbasis.ui.base.BaseView
import io.realm.RealmList

interface DetailsContract {

    interface View : BaseView {
        fun onEditPersonResponse(person: Person)
        fun onCreatePersonResponse(person: Person)
        fun onGetPersonResponse(person: Person)
        fun onCreateAddressResponse(address: Address)
        fun onRemoveAddressResponse(address: Address)
    }

    interface Presenter : BasePresenter<View> {
        fun editPerson(person: Person)
        fun createPerson(person: Person)
        fun getPersonById(personId: String)
        fun createAddress(address: Address)
        fun removeAddress(address: Address)
        fun getAddressList(): RealmList<Address>
    }
}