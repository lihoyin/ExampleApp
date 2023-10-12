package com.example.app.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.app.data.model.Item
import com.example.app.databinding.ListItemBinding

class ItemListAdapter(private val listener: ItemListListener) :
    ListAdapter<Item, ItemListAdapter.ItemViewHolder>(ItemDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemListAdapter.ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ItemViewHolder(
            ListItemBinding.inflate(inflater, parent, false),
            listener
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ItemViewHolder(
        private val binding: ListItemBinding,
        private val listener: ItemListListener
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener { listener.onItemPressed(getItem(adapterPosition)) }
        }

        fun bind(item: Item) {
            binding.tvTitle.text = item.title
            binding.tvDescription.text = item.description
        }
    }
}

class ItemDiffCallback : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }
}

fun interface ItemListListener {
    fun onItemPressed(item: Item)
}
