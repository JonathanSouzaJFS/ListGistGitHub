package br.com.listgistgithub.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import br.com.listgistgithub.databinding.ItemGistBinding
import br.com.listgistgithub.model.Gist

class HomeAdapter(
    private val context: Context,
    private val list: ArrayList<Gist>,
    private val onItemClickListener: ((gist: Gist) -> Unit)
) : RecyclerView.Adapter<HomeAdapter.DataViewHolder>() {

    private var lastPosition = -1

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
            clear()
            addAll(gists)
        }
    }
}
