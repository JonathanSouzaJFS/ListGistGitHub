package br.com.testbasis.ui.home

import HomeContract
import android.app.AlertDialog
import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import br.com.testbasis.R
import br.com.testbasis.data.model.Person
import br.com.testbasis.databinding.FragmentHomeBinding
import br.com.testbasis.ui.adapter.HomeAdapter
import br.com.testbasis.utils.EMPTY
import br.com.testbasis.utils.showAlertDialog
import br.com.testbasis.ui.base.BasePresenterFragment
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class HomeFragment : BasePresenterFragment<HomeContract.Presenter>(), HomeContract.View,
    SwipeRefreshLayout.OnRefreshListener,
    SearchView.OnQueryTextListener {

    override val presenter: HomeContract.Presenter by inject { parametersOf(this) }
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()
        setupRecyclerViewAdapter()

        presenter.getPersonAll()

        binding.swiperefresh.setOnRefreshListener(this)
        setHasOptionsMenu(true)

        binding.createPerson.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(
                EMPTY
            )
            requireView().findNavController().navigate(action)
        }
    }

    private fun checkRecyclerViewIsNull() {
        if (adapter.itemCount == 0) {
            binding.recyclerViewPerson.visibility = View.GONE
            binding.emptyView.visibility = View.VISIBLE
        } else {
            binding.recyclerViewPerson.visibility = View.VISIBLE
            binding.emptyView.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.search_menu, menu)

        val searchManager =
            requireContext().getSystemService(Context.SEARCH_SERVICE) as SearchManager?
        val searchMenuItem = menu.findItem(R.id.search)
        val searchView = searchMenuItem.actionView as SearchView
        searchView.setSearchableInfo(searchManager!!.getSearchableInfo(requireActivity().componentName))
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(this)
    }

    private fun setupRecyclerView() {
        binding.recyclerViewPerson.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewPerson.setHasFixedSize(true)
    }

    private fun setupRecyclerViewAdapter() {
        adapter = HomeAdapter(requireContext(), arrayListOf())

        adapter.deletePersonListener {
            showDialogDeletePerson(it)
        }
        adapter.editPersonListener {
            val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(
                it.id
            )
            requireView().findNavController().navigate(action)
        }
        binding.recyclerViewPerson.adapter = adapter
    }

    override fun onRefresh() {
        presenter.getPersonAll()
    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        adapter.filter.filter(p0)
        return false
    }

    override fun onDeletePersonResponse(person: Person) {
        adapter.removeItem(person)

        updateRecyclerView()
    }

    override fun onGetAllPersonResponse(list: MutableList<Person>) {
        adapter.addAllPerson(list)
        updateRecyclerView()
    }

    private fun updateRecyclerView() {
        checkRecyclerViewIsNull()
        binding.swiperefresh.isRefreshing = false
        adapter.notifyDataSetChanged()
    }

    private var alertDialog: AlertDialog? = null

    private fun showDialogDeletePerson(person: Person) {

        alertDialog = showAlertDialog {
            cancelable = false

            setTitle(getString(R.string.title_dialog_delete))
            setBody(getString(R.string.description_dialog_delete))

            prepareOkayButton(getString(R.string.title_yes), "#A7DE3D")
            prepareCancelButton(getString(R.string.title_not), "#F77B7B")

            okClickListener {
                presenter.deletePerson(person)
                Toast.makeText(
                    requireContext(),
                    getString(R.string.success_delete_person),
                    Toast.LENGTH_SHORT
                ).show()
                alertDialog?.dismiss()
            }

            cancelClickListener {
                alertDialog?.dismiss()
            }
        }

        alertDialog?.show()
    }
}