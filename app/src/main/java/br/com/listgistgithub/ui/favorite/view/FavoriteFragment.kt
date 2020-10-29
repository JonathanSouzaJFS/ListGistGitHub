package br.com.listgistgithub.ui.favorite.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import br.com.listgistgithub.R
import br.com.listgistgithub.model.Gist
import br.com.listgistgithub.ui.adapter.HomeAdapter
import br.com.listgistgithub.ui.favorite.viewmodel.FavoriteViewModel
import kotlinx.android.synthetic.main.fragment_favorites.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class FavoriteFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private val viewModel by viewModel<FavoriteViewModel>()
    private lateinit var adapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getFavorites(requireContext())
        setupRecyclerView()
        setupRecyclerViewAdapter()
        setupObservers()
        swiperefresh.setOnRefreshListener(this)
    }

    private fun setupRecyclerView() {
        recyclerViewFavoriteList.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewFavoriteList.setHasFixedSize(true)
    }

    private fun setupRecyclerViewAdapter() {
        adapter = HomeAdapter(requireContext(), arrayListOf()) {
            val action = FavoriteFragmentDirections.actionFavoritesFragmentToDetailsFragment(
                it.owner!!.login!!, it.owner!!.avatarUrl!!, it.description!!
            )
            requireView().findNavController().navigate(action)
        }
        adapter.setOnUnFavoriteClickListener {
            viewModel.deleteFavorite(requireContext(), it.id!!)
            adapter.removeItem(it)
            adapter.notifyDataSetChanged()
        }
        recyclerViewFavoriteList.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.listFavorite.observe(requireActivity(), Observer {
            retrieveList(viewModel.convertFavoriteToGist(it))
        })
        viewModel.loading.observe(viewLifecycleOwner, Observer { load ->
            swiperefresh.isRefreshing = load
        })
    }

    private fun retrieveList(gists: List<Gist>) {
        adapter.apply {
            clearItems()
            addGist(gists)
            swiperefresh.isRefreshing = false
            notifyDataSetChanged()
        }
    }

    override fun onRefresh() {
        viewModel.getFavorites(requireContext())
    }
}