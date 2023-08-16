import br.com.testbasis.data.model.Person
import br.com.testbasis.ui.base.BasePresenter
import br.com.testbasis.ui.base.BaseView

interface HomeContract {

    interface View : BaseView {
        fun onDeletePersonResponse(person: Person)
        fun onGetAllPersonResponse(list: MutableList<Person>)
    }

    interface Presenter : BasePresenter<View> {
        fun deletePerson(person: Person)
        fun getPersonAll()
    }
}