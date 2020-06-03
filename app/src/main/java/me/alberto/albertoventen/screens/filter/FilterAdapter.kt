package me.alberto.albertoventen.screens.filter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.alberto.albertoventen.databinding.FilterItemBinding
import me.alberto.albertoventen.model.FilterItem

class FilterAdapter(
    private val clickListener: FilterItemClickListener
) :
    ListAdapter<FilterItem, RecyclerView.ViewHolder>(DiffCallback()) {

    class DiffCallback : DiffUtil.ItemCallback<FilterItem>() {
        override fun areItemsTheSame(oldItem: FilterItem, newItem: FilterItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FilterItem, newItem: FilterItem): Boolean {
            return oldItem == newItem
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FilterItemHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FilterItemHolder) {
            val item = getItem(position)
            holder.bind(item, clickListener)
        }
    }


    class FilterItemHolder(private val binding: FilterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            filterItem: FilterItem,
            clickListener: FilterItemClickListener
        ) {
            binding.filterItem = filterItem
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): FilterItemHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = FilterItemBinding.inflate(inflater, parent, false)
                return FilterItemHolder(binding)
            }
        }
    }

}

class FilterItemClickListener(val clickListener: (filterItem: FilterItem) -> Unit) {
    fun onClick(filterItem: FilterItem) = clickListener(filterItem)
}