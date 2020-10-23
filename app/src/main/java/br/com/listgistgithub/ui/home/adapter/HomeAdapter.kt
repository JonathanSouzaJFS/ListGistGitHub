package br.com.listgistgithub.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.listgistgithub.R
import br.com.listgistgithub.model.Gist
import kotlinx.android.synthetic.main.item_layout.view.*

class HomeAdapter(private val gist: ArrayList<Gist>) :
    RecyclerView.Adapter<HomeAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(gist: Gist) {
            itemView.apply {
                nameGistOwner.text = gist.owner!!.login
                typeGist.text = gist.description
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        )

    override fun getItemCount(): Int = gist.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(gist[position])
    }

    fun addGist(gists: List<Gist>) {
        this.gist.apply {
            clear()
            addAll(gists)
        }
    }
}
