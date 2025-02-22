package com.example.idtex.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.idtex.R
import com.example.idtex.databinding.ItemStateBinding
import com.example.idtex.entities.State

/**
 * Adapter class for displaying a list of states in a RecyclerView.
 */
class StateAdapter : RecyclerView.Adapter<StateAdapter.StateViewHolder>() {

    companion object {
        /**
         * Callback for calculating the diff between two non-null states in a list.
         */
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<State>() {
            override fun areItemsTheSame(oldItem: State, newItem: State): Boolean {
                // Compare unique IDs or relevant fields
                return oldItem.state == newItem.state
            }

            override fun areContentsTheSame(oldItem: State, newItem: State): Boolean {
                return oldItem == newItem
            }
        }
    }

    /**
     * AsyncListDiffer to handle list updates asynchronously.
     */
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    /**
     * List holding all states data.
     */
    private var allStates = listOf<State>()

    /**
     * Click listener for handling state item clicks.
     */
    var onItemClick = { _: State -> }

    /**
     * ViewHolder class for state items.
     */
    inner class StateViewHolder(
        private val binding: ItemStateBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        /**
         * Binds the state data to the view.
         */
        fun bind(state: State, isHighlighted: Boolean) {
            binding.stateName.text = state.state
            binding.root.setOnClickListener {
                onItemClick(state)
                // Clear Selected State
                clearHighlight()
            }
            binding.root.setOnLongClickListener {
                onItemLongClickListener?.invoke(state)
                true
            }

            itemView.setBackgroundColor(
                if (isHighlighted) itemView.context.getColor(R.color.highlight_color)
                else itemView.context.getColor(android.R.color.transparent)
            )
        }
    }

    /**
     * Called when RecyclerView needs a new {@link StateViewHolder} of the given type to represent an item.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StateViewHolder {
        val binding = ItemStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StateViewHolder(binding)
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     */
    override fun onBindViewHolder(holder: StateViewHolder, position: Int) {
        val state = differ.currentList[position]
        holder.bind(state, state == highlightedState)
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     */
    override fun getItemCount(): Int = differ.currentList.size

    /**
     * Submits a new list of states to the adapter.
     */
    fun submitList(newStates: List<State>) {
        allStates = newStates
        differ.submitList(newStates)
    }

    /**
     * Filters the state list based on the given query and updates the adapter.
     */
    fun filter(query: String) {
        val filteredStateList = if (query.isEmpty()) {
            allStates
        } else {
            allStates.filter { it.state.toString().contains(query, ignoreCase = true) }
        }
        differ.submitList(filteredStateList)
    }

    /**
     * Returns the current filtered list of states.
     */
    fun getFilteredList(): List<State> {
        return differ.currentList
    }

    private var onItemLongClickListener: ((State) -> Unit)? = null
    private var highlightedState: State? = null

    fun setOnItemLongClickListener(listener: (State) -> Unit) {
        onItemLongClickListener = listener
    }

    fun highlightState(state: State?) {
        val previousIndex = differ.currentList.indexOf(highlightedState)
        highlightedState = state
        val currentIndex = differ.currentList.indexOf(state)

        // Unhighlight the previous item
        if (previousIndex != -1) notifyItemChanged(previousIndex)

        // Highlight the new item
        if (currentIndex != -1) notifyItemChanged(currentIndex)
    }

    fun clearHighlight() {
        val selected = differ.currentList.indexOf(highlightedState)
        highlightedState = null
        if (selected != -1) notifyItemChanged(selected)
    }

}