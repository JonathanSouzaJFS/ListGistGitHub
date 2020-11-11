package br.com.listgistgithub.ui.home.view

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import br.com.listgistgithub.R
import br.com.listgistgithub.model.Gist
import br.com.listgistgithub.ui.adapter.HomeAdapter
import br.com.listgistgithub.ui.home.viewmodel.HomeViewModel
import br.com.listgistgithub.utils.NetworkResponse
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener,
    SearchView.OnQueryTextListener {

    private val viewModel by viewModel<HomeViewModel>()

    private lateinit var adapter: HomeAdapter
    private var pastVisiblesItems = 0
    private var totalItemCount = 0
    private var loading = false
    private var pageLoad = 0
    private var searching = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()
        setupRecyclerViewAdapter()
        viewModel.getFavorites(requireActivity())
        setupObservers()
        swiperefresh.setOnRefreshListener(this)
        setHasOptionsMenu(true)
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
        recyclerViewGists.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewGists.setHasFixedSize(true)
        recyclerViewGists.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0 && !searching) addingMoreGists()
            }
        })
    }

    private fun setupRecyclerViewAdapter() {
        adapter = HomeAdapter(requireContext(), arrayListOf()) {
            val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(
                it.owner.login, it.owner.avatarUrl, it.description
            )
            requireView().findNavController().navigate(action)
        }
        adapter.setOnFavoriteClickListener {
            viewModel.insertFavorite(requireContext(), it)
        }
        adapter.setOnUnFavoriteClickListener {
            viewModel.deleteFavorite(requireContext(), it.id)
        }
        recyclerViewGists.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.getGirts(requireContext(), pageLoad).observe(requireActivity(), Observer {
            it?.let { resource ->
                when (resource) {
                    is NetworkResponse.Success -> {
                        progressBar.visibility = View.GONE // Crash
                        retrieveList(resource.data)
                        loading = false
                        swiperefresh.isRefreshing = false
                    }
                    is NetworkResponse.Error -> {
                        progressBar.visibility = View.GONE // Crash
                        Toast.makeText(requireContext(), resource.exception, Toast.LENGTH_LONG)
                            .show()
                        loading = false
                        swiperefresh.isRefreshing = false
                    }
                    is NetworkResponse.Loading -> {
                        progressBar.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun addingMoreGists() {
        totalItemCount = recyclerViewGists.layoutManager!!.itemCount
        pastVisiblesItems =
            (recyclerViewGists.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

        if (!loading) {
            if (pastVisiblesItems >= totalItemCount - 1) {
                loading = true
                pageLoad += 1
                viewModel.getFavorites(requireActivity())
                setupObservers()
            }
        }
    }

    private fun retrieveList(gists: List<Gist>) {
        viewModel.setGistsFavorites(gists)
        adapter.apply {
            addGist(gists)
            notifyDataSetChanged()
        }
    }

    override fun onRefresh() {
        adapter.clearItems()
        viewModel.getFavorites(requireActivity())
        setupObservers()
    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(p0: String?): Boolean {

        p0?.let {
            searching = p0.isNotEmpty()
        }
        adapter.filter.filter(p0)
        return false
    }
}