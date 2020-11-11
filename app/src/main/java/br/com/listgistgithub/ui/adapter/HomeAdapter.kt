package br.com.listgistgithub.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import br.com.listgistgithub.R
import br.com.listgistgithub.databinding.ItemGistBinding
import br.com.listgistgithub.model.Gist
import java.util.*
import kotlin.collections.ArrayList

class HomeAdapter(
    private val context: Context,
    private val list: ArrayList<Gist>,
    private val onItemClickListener: ((gist: Gist) -> Unit)
) : RecyclerView.Adapter<HomeAdapter.DataViewHolder>(), Filterable {

    private var lastPosition = -1
    lateinit var favoriteListener: OnFavoriteClickListener
    lateinit var listGistFilter: List<Gist>
    lateinit var listGistFull: ArrayList<Gist>
    lateinit var unFavoriteListener: OnUnFavoriteClickListener

    inner class DataViewHolder(
        private val binding: ItemGistBinding,
        private val onItemClickListener: ((gist: Gist) -> Unit)
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(gist: Gist) {
            binding.gist = gist

            binding.root.setOnClickListener {
                onItemClickListener.invoke(gist)
            }

            binding.iconFavoriteGist.setBackgroundResource(if (gist.isFavorite) R.drawable.ic_heart_pressed else R.drawable.ic_heart)
            binding.iconFavoriteGist.setOnClickListener {
                if (!gist.isFavorite) favoriteListener.onClick(gist) else unFavoriteListener.onClick(
                    gist
                )
                gist.isFavorite = !gist.isFavorite
                it.setBackgroundResource(if (gist.isFavorite) R.drawable.ic_heart_pressed else R.drawable.ic_heart)
            }
        }
    }

    private fun setAnimation(viewToAnimate: View, position: Int) {
        if (position > lastPosition) {
            val animation: Animation =
                AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left)
            viewToAnimate.startAnimation(animation)
            lastPosition = position
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
        setAnimation(holder.itemView, position)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                listGistFilter = if (charSearch.isEmpty()) {
                    listGistFull
                } else {
                    val resultList = listGistFull.filter {
                        (it.owner.login).toLowerCase(Locale.ROOT)
                            .contains(charSearch.toLowerCase(Locale.ROOT))
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = listGistFilter
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                results?.let {
                    it.values?.let {
                        listGistFilter = results.values as ArrayList<Gist>
                        clearItems()
                        list.addAll(listGistFilter)
                        notifyDataSetChanged()
                    }
                }
            }
        }
    }

    fun addGist(gists: List<Gist>) {
        list.addAll(gists)
        listGistFull = ArrayList(list)
    }

    fun clearItems() = list.clear()

    fun removeItem(gist: Gist) = list.remove(gist)

    inline fun setOnFavoriteClickListener(crossinline listener: (Gist) -> Unit) {
        this.favoriteListener = object :
            OnFavoriteClickListener {
            override fun onClick(gist: Gist) = listener(gist)
        }
    }

    inline fun setOnUnFavoriteClickListener(crossinline listener: (Gist) -> Unit) {
        this.unFavoriteListener = object :
            OnUnFavoriteClickListener {
            override fun onClick(gist: Gist) = listener(gist)
        }
    }

    interface OnFavoriteClickListener {
        fun onClick(gist: Gist) = Unit
    }

    interface OnUnFavoriteClickListener {
        fun onClick(gist: Gist) = Unit
    }
}
