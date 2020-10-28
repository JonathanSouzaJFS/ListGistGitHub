package br.com.listgistgithub.ui.favorite.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.listgistgithub.R
import br.com.listgistgithub.model.Gist
import br.com.listgistgithub.ui.favorite.viewmodel.FavoriteViewModel
import br.com.listgistgithub.ui.home.adapter.HomeAdapter
import kotlinx.android.synthetic.main.fragment_favorites.*

class FavoritesFragment : Fragment() {

    private lateinit var viewModel: FavoriteViewModel
    private lateinit var adapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setupViewModel()

        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getFavorites(requireContext())
        setupRecyclerView()
        setupRecyclerViewAdapter()
        setupObservers()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
    }

    private fun setupRecyclerView() {
        recyclerViewFavoriteList.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewFavoriteList.setHasFixedSize(true)
    }

    private fun setupRecyclerViewAdapter() {
        adapter = HomeAdapter(requireContext(), arrayListOf()) {
            val action = FavoritesFragmentDirections.actionFavoritesFragmentToDetailsFragment(
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
    }

    private fun retrieveList(gists: List<Gist>) {
        adapter.apply {
            addGist(gists)
            notifyDataSetChanged()
        }
    }
}