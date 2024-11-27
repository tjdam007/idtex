package com.example.idtex.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.idtex.databinding.ItemCountyBinding

/**
 * Adapter class for displaying a list of counties in a RecyclerView.
 */
class CountyAdapter : RecyclerView.Adapter<CountyAdapter.CountyViewHolder>() {

    /**
     * Companion object holding the DiffUtil callback for comparing county names.
     */
    companion object {
        /**
         * Callback for calculating the diff between two non-null items in a list.
         */
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }
    }

    /**
     * AsyncListDiffer to handle list updates asynchronously.
     */
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    /**
     * Called when RecyclerView needs a new [CountyViewHolder] of the given type to represent an item.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountyViewHolder {
        val binding = ItemCountyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CountyViewHolder(binding)
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     */
    override fun onBindViewHolder(holder: CountyViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     */
    override fun getItemCount(): Int = differ.currentList.size

    /**
     * Submits a new list of counties to the adapter.
     */
    fun submitList(newStates: List<String>) {
        differ.submitList(newStates)
    }

    /**
     * ViewHolder class for county items.
     */
    inner class CountyViewHolder(
        private val binding: ItemCountyBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        /**
         * Binds the county name to the view.
         */
        fun bind(countyName: String) {
            binding.countyName.text = countyName
        }
    }
}