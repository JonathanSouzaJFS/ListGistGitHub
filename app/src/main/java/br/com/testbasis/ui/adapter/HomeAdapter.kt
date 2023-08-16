package br.com.testbasis.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import br.com.testbasis.data.model.Person
import br.com.testbasis.databinding.ItemPersonBinding
import java.util.Locale

class HomeAdapter(
    private val context: Context,
    private val list: ArrayList<Person>
) : RecyclerView.Adapter<HomeAdapter.DataViewHolder>(), Filterable {

    private var lastPosition = -1
    lateinit var editPersonListener: OnEditPersonClickListener
    lateinit var deletePersonListener: OnDeletePersonClickListener

    lateinit var listPersonFilter: List<Person>
    lateinit var listPersonFull: ArrayList<Person>


    inner class DataViewHolder(
        private val binding: ItemPersonBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(person: Person) {
            binding.person = person

            binding.iconFavoritePerson.setOnClickListener {
                editPersonListener.onClick(person)
            }

            binding.iconCommentsPerson.setOnClickListener {
                deletePersonListener.onClick(person)
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
        val binding = ItemPersonBinding.inflate(inflater, parent, false)
        return DataViewHolder(binding)
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
                listPersonFilter = if (charSearch.isEmpty()) {
                    listPersonFull
                } else {
                    val resultList = listPersonFull.filter {
                        if (it.isCompany) {
                            (it.corporateName).lowercase(Locale.ROOT)
                                .contains(charSearch.lowercase(Locale.ROOT))
                        } else {
                            (it.name).lowercase(Locale.ROOT)
                                .contains(charSearch.lowercase(Locale.ROOT))
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = listPersonFilter
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                results?.let {
                    it.values?.let {
                        listPersonFilter = results.values as ArrayList<Person>
                        clearItems()
                        list.addAll(listPersonFilter)
                        notifyDataSetChanged()
                    }
                }
            }
        }
    }

    fun addAllPerson(person: List<Person>) {
        clearItems()
        list.addAll(person)
        listPersonFull = ArrayList(list)
    }

    private fun clearItems() = list.clear()

    fun removeItem(person: Person) = list.remove(person)

    inline fun editPersonListener(crossinline listener: (Person) -> Unit) {
        this.editPersonListener = object :
            OnEditPersonClickListener {
            override fun onClick(person: Person) = listener(person)
        }
    }

    inline fun deletePersonListener(crossinline listener: (Person) -> Unit) {
        this.deletePersonListener = object :
            OnDeletePersonClickListener {
            override fun onClick(person: Person) = listener(person)
        }
    }

    interface OnEditPersonClickListener {
        fun onClick(person: Person) = Unit
    }

    interface OnDeletePersonClickListener {
        fun onClick(person: Person) = Unit
    }
}
