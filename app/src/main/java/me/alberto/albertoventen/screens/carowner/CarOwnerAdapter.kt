package me.alberto.albertoventen.screens.carowner

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.alberto.albertoventen.databinding.CarOwnerItemBinding
import me.alberto.albertoventen.model.CarOwner
import me.alberto.albertoventen.model.FilterItem

class CarOwnerAdapter :
    ListAdapter<CarOwner, RecyclerView.ViewHolder>(DiffCallback()) {

    class DiffCallback : DiffUtil.ItemCallback<CarOwner>() {
        override fun areItemsTheSame(oldItem: CarOwner, newItem: CarOwner): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CarOwner, newItem: CarOwner): Boolean {
            return oldItem == newItem
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CarOwnerHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CarOwnerHolder) {
            val item = getItem(position)
            holder.bind(item)
        }
    }


    class CarOwnerHolder(private val binding: CarOwnerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            carOwner: CarOwner
        ) {
            binding.carOwner = carOwner
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): CarOwnerHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = CarOwnerItemBinding.inflate(inflater, parent, false)
                return CarOwnerHolder(binding)
            }
        }
    }

}
