package br.com.listgistgithub.ui.home.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.listgistgithub.R
import br.com.listgistgithub.data.api.ApiHelper
import br.com.listgistgithub.data.api.RetrofitBuilder
import br.com.listgistgithub.model.Gist
import br.com.listgistgithub.ui.base.ViewModelFactory
import br.com.listgistgithub.ui.home.adapter.HomeAdapter
import br.com.listgistgithub.ui.home.viewmodel.HomeViewModel
import br.com.listgistgithub.utils.Status
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var adapter: HomeAdapter
    private var pastVisiblesItems = 0
    private var totalItemCount = 0
    private var loading = false
    private var pageLoad = 0
    private var totalPages = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setupViewModel()

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupUI()
        setupObservers()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(HomeViewModel::class.java)
    }

    private fun setupUI() {
        recyclerViewGists.layoutManager = LinearLayoutManager(requireContext())
        adapter = HomeAdapter(requireContext(), arrayListOf()) { gist ->
            // OOther view
        }

        recyclerViewGists.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(
                recyclerView: RecyclerView, dx: Int, dy: Int
            ) {
                if (dy > 0) {
                    Log.i(
                        "ResultadoJFS",
                        "dy > 0 (pageLoad: " + pageLoad + ") (totalPages: " + totalPages + ")"
                    );

                    totalItemCount = recyclerViewGists.layoutManager!!.itemCount // Total da lista
                    pastVisiblesItems =
                        (recyclerViewGists.layoutManager as LinearLayoutManager).findLastVisibleItemPosition() // Itens visiveis na tela
                    Log.i(
                        "ResultadoJFS",
                        "(pastVisiblesItems " + pastVisiblesItems + ") (totalItemCount: " + totalItemCount + ")"
                    );

                    if (!loading) {
                        if (pastVisiblesItems >= totalItemCount - 1) {
                            loading = true
                            pageLoad += 1
                            Log.i(
                                "ResultadoJFS",
                                "Entrou aqui pra atualizar: Page: " + pageLoad + " - 30 itens."
                            )
                            setupObservers()
                        }
                    }
                }
            }
        })

        recyclerViewGists.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.getGirts(pageLoad, 30).observe(requireActivity(), Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        progressBar.visibility = View.GONE
                        resource.data?.let { gists -> retrieveList(gists) }
                        loading = false
                    }
                    Status.ERROR -> {
                        progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                        loading = false
                    }
                    Status.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                        //       recyclerViewGists.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun retrieveList(gists: List<Gist>) {
        adapter.apply {
            addGist(gists)
            notifyDataSetChanged()
        }
    }
}