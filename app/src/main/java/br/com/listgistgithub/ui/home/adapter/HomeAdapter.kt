package br.com.listgistgithub.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import br.com.listgistgithub.R
import br.com.listgistgithub.databinding.ItemGistBinding
import br.com.listgistgithub.model.Gist

class HomeAdapter(
    private val context: Context,
    private val list: ArrayList<Gist>,
    private val onItemClickListener: ((gist: Gist) -> Unit)
) : RecyclerView.Adapter<HomeAdapter.DataViewHolder>() {

    private var lastPosition = -1
    lateinit var favoriteListener: OnFavoriteClickListener
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
            binding.iconFavoriteGist.setOnClickListener {
                it.setBackgroundResource(if (gist.isFavorite) R.drawable.ic_heart_pressed else R.drawable.ic_heart)
                favoriteListener.onClick(gist)
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

    fun addGist(gists: List<Gist>) {
        this.list.apply {
            addAll(gists)
        }
    }

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
