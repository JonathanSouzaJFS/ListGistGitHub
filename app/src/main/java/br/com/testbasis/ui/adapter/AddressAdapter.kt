package br.com.testbasis.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import br.com.testbasis.data.model.Address
import br.com.testbasis.databinding.ItemAddressBinding
import io.realm.RealmList

class AddressAdapter(
    private val context: Context,
    private val list: RealmList<Address>
) : RecyclerView.Adapter<AddressAdapter.DataViewHolder>() {

    private var lastPosition = -1
    lateinit var deleteAddressListener: OnDeleteAddressClickListener


    inner class DataViewHolder(
        private val binding: ItemAddressBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(address: Address?) {
            binding.address = address

            binding.iconDeleteAddress.setOnClickListener {
                address?.let { deleteAddressListener.onClick(it) }
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
        val binding = ItemAddressBinding.inflate(inflater, parent, false)
        return DataViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(list[position])
        setAnimation(holder.itemView, position)
    }

    fun addAllAddress(address: RealmList<Address>) {
        clearItems()
        list.addAll(address)
    }

    fun addAddress(address: Address) {
        list.add(address)
    }

    private fun clearItems() = list.clear()

    fun removeItem(address: Address) = list.remove(address)

    inline fun deleteAddressListener(crossinline listener: (Address) -> Unit) {
        this.deleteAddressListener = object :
            OnDeleteAddressClickListener {
            override fun onClick(address: Address) = listener(address)
        }
    }

    interface OnDeleteAddressClickListener {
        fun onClick(address: Address) = Unit
    }
}
