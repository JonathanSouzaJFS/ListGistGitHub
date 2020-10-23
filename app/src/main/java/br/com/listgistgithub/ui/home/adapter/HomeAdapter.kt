package br.com.listgistgithub.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.listgistgithub.databinding.ItemGistBinding
import br.com.listgistgithub.model.Gist

class HomeAdapter(
    private val context: Context,
    private val list: ArrayList<Gist>,
    private val onItemClickListener: ((gist: Gist) -> Unit)
) : RecyclerView.Adapter<HomeAdapter.DataViewHolder>() {

    inner class DataViewHolder(
        private val binding: ItemGistBinding,
        private val onItemClickListener: ((gist: Gist) -> Unit)
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(gist: Gist) {
            binding.gist = gist
            binding.root.setOnClickListener {
                onItemClickListener.invoke(gist)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = ItemGistBinding.inflate(inflater, parent, false)
        return DataViewHolder(binding, onItemClickListener)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun addGist(gists: List<Gist>) {
        this.list.apply {
            clear()
            addAll(gists)
        }
    }
}
