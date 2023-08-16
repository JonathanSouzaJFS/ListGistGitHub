package br.com.testbasis.ui.details

import DetailsContract
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.testbasis.R
import br.com.testbasis.data.model.Address
import br.com.testbasis.data.model.Person
import br.com.testbasis.databinding.FragmentDetailsBinding
import br.com.testbasis.ui.adapter.AddressAdapter
import br.com.testbasis.utils.EMPTY
import br.com.testbasis.utils.isCNPJ
import br.com.testbasis.utils.isCPF
import br.com.testbasis.utils.isPhoneValid
import br.com.testbasis.utils.isValidEmail
import br.com.testbasis.utils.showCreateAddress
import br.com.testbasis.ui.base.BasePresenterFragment
import io.realm.RealmList
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf


class DetailsFragment : BasePresenterFragment<DetailsContract.Presenter>(), DetailsContract.View {

    override val presenter: DetailsContract.Presenter by inject { parametersOf(this) }
    private val args: DetailsFragmentArgs by navArgs()
    private lateinit var adapter: AddressAdapter
    private lateinit var binding: FragmentDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setupRecyclerView()
        setupRecyclerViewAdapter()

        if (!args.id.isNullOrEmpty()) {
            presenter.getPersonById(args.id.toString())
            binding.btnOk.text = getString(R.string.btn_update)
        }
        setListeners()
    }

    private fun setListeners() {

        binding.radioPerson.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.apply {
                    personCNPJ.visibility = View.GONE
                    personCorporateName.visibility = View.GONE
                    personCpf.visibility = View.VISIBLE
                    personName.visibility = View.VISIBLE
                }
            }
        }

        binding.radioCompany.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.apply {
                    personCpf.visibility = View.GONE
                    personName.visibility = View.GONE
                    personCNPJ.visibility = View.VISIBLE
                    personCorporateName.visibility = View.VISIBLE
                }
            }
        }

        binding.btnCreateAddress.setOnClickListener {
            showCreateAddressDialog()
        }

        binding.btnOk.setOnClickListener {
            if (validateFields()) {
                binding.apply {
                    val person = createNewPerson()

                    // Create or Update
                    if (args.id.isNullOrEmpty()) {
                        presenter.createPerson(person)
                    } else {
                        person.id = args.id.toString()
                        presenter.editPerson(person)
                    }
                }
            }
        }
    }

    private fun createNewPerson(): Person {
        binding.apply {
            return Person(
                if (selectedRadioPerson()) personName.text.toString() else EMPTY,
                selectedRadioCompany(),
                if (selectedRadioPerson()) personCpf.text.toString() else EMPTY,
                if (selectedRadioCompany()) personCorporateName.text.toString() else EMPTY,
                if (selectedRadioCompany()) personCNPJ.text.toString() else EMPTY,
                personPhone.text.toString(),
                personMail.text.toString(),
                presenter.getAddressList()
            )
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerViewAddress.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewAddress.setHasFixedSize(true)
    }

    private fun setupRecyclerViewAdapter() {
        adapter = AddressAdapter(requireContext(), RealmList())

        adapter.deleteAddressListener {
            presenter.removeAddress(it)
        }
        binding.recyclerViewAddress.adapter = adapter
    }

    override fun onEditPersonResponse(person: Person) {
        if (selectedRadioPerson()) {
            Toast.makeText(requireActivity(), getString(R.string.success_edit_address), Toast.LENGTH_SHORT)
                .show()
        } else {
            Toast.makeText(
                requireActivity(),
                getString(R.string.success_edit_company),
                Toast.LENGTH_SHORT
            )
                .show()
        }

        Navigation.findNavController(requireView()).popBackStack()
    }

    override fun onCreatePersonResponse(person: Person) {
        if (selectedRadioPerson()) {
            Toast.makeText(requireActivity(), getString(R.string.success_create_address), Toast.LENGTH_SHORT)
                .show()
        } else {
            Toast.makeText(requireActivity(), getString(R.string.success_create_company), Toast.LENGTH_SHORT)
                .show()
        }

        Navigation.findNavController(requireView()).popBackStack()
    }

    override fun onGetPersonResponse(person: Person) {
        binding.apply {
            if (person.isCompany) {
                radioGroup.check(R.id.radio_company)
                personCpf.setText(EMPTY)
                personName.setText(EMPTY)
                personCpf.visibility = View.GONE
                personName.visibility = View.GONE
                personCNPJ.visibility = View.VISIBLE
                personCorporateName.visibility = View.VISIBLE
            } else {
                radioGroup.check(R.id.radio_person)
                personCNPJ.setText(EMPTY)
                personCorporateName.setText(EMPTY)
            }
            personCNPJ.setText(person.cnpj)
            personCpf.setText(person.cpf)
            personCorporateName.setText(person.corporateName)
            personName.setText(person.name)
            personMail.setText(person.email)
            personPhone.setText(person.phone)
            adapter.addAllAddress(person.address)
        }
    }

    override fun onCreateAddressResponse(address: Address) {
        Toast.makeText(requireContext(), getString(R.string.success_address), Toast.LENGTH_SHORT)
            .show()

        adapter.addAddress(address)
        adapter.notifyDataSetChanged()
    }

    override fun onRemoveAddressResponse(address: Address) {
        adapter.removeItem(address)
        adapter.notifyDataSetChanged()
    }

    private fun showCreateAddressDialog() {

        alertDialog = showCreateAddress {
            cancelable = false

            okClickListener {
                presenter.createAddress(it)
                alertDialog?.dismiss()
            }

            cancelClickListener {
                alertDialog?.dismiss()
            }
        }

        alertDialog?.show()
    }

    private fun selectedRadioPerson(): Boolean {
        return binding.radioPerson.isChecked
    }

    private fun selectedRadioCompany(): Boolean {
        return binding.radioCompany.isChecked
    }

    private fun validateFields(): Boolean {
        var validateFields = true
        binding.apply {
            if (!selectedRadioPerson()) {
                if (personCorporateName.text.toString().isEmpty()) {
                    personCorporateName.error = getString(R.string.error_corporateName)
                    validateFields = false
                }
                if (personCNPJ.text.toString().isEmpty() || !personCNPJ.text.toString().isCNPJ()) {
                    personCNPJ.error = getString(R.string.error_cnpj)
                    validateFields = false
                }
            } else {
                if (personName.text.toString().isEmpty()) {
                    personName.error = getString(R.string.error_name)
                    validateFields = false
                }
                if (personCpf.text.toString().isEmpty() || !personCpf.text.toString().isCPF()) {
                    personCpf.error = getString(R.string.error_cpf)
                    validateFields = false
                }
            }

            if (!personMail.text.toString().isValidEmail() || personMail.text.toString()
                    .isEmpty()
            ) {
                personMail.error = getString(R.string.error_email)
                validateFields = false
            }

            if (!personPhone.text.toString().isPhoneValid() || personPhone.text.toString()
                    .isEmpty()
            ) {
                personPhone.error = getString(R.string.error_phone)
                validateFields = false
            }

            if (presenter.getAddressList().isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.error_address),
                    Toast.LENGTH_SHORT
                )
                    .show()
                validateFields = false
            }
        }
        return validateFields
    }

    private var alertDialog: AlertDialog? = null

}