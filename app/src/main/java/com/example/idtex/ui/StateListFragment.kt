package com.example.idtex.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.idtex.databinding.FragmentStateListBinding
import  com.example.idtex.entities.State
import com.example.idtex.repositories.StateRepository
import com.example.idtex.utils.DATA
import com.example.idtex.utils.SELECTED_STATE
import com.example.idtex.viewmodels.StateViewModel
import com.example.idtex.viewmodels.StateViewModelFactory
import kotlin.getValue

/**
 * A Fragment that displays a list of states and handles user interactions.
 */
class StateListFragment : Fragment() {

    private var _binding: FragmentStateListBinding? = null
    private val binding get() = _binding!!
    private var stateChangedHere = false

    private val adapter = StateAdapter()

    private val stateViewModel: StateViewModel by activityViewModels {
        StateViewModelFactory(
            StateRepository()
        )
    }


    /**
     * Called to have the fragment instantiate its user interface view.
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStateListBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Called when the fragment's view has been created.
     * @param view The View returned by onCreateView(LayoutInflater, ViewGroup, Bundle).
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.statesRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.statesRecyclerView.adapter = adapter
        adapter.onItemClick = { selectedState ->
            requireActivity().supportFragmentManager.setFragmentResult(
                SELECTED_STATE, bundleOf(DATA to selectedState)
            )
        }
        adapter.setOnItemLongClickListener { state ->
            stateChangedHere = true
            stateViewModel.selectState(state)
        }

        stateViewModel.selectedState.observe(viewLifecycleOwner) { state ->
            if (stateChangedHere) {
                adapter.highlightState(state)
            }else{
                adapter.clearHighlight()
            }
            stateChangedHere = false
        }
    }

    /**
     * Sets the data for the adapter.
     * @param data The list of State objects to be displayed.
     */
    fun setData(data: List<State>) {
        adapter.submitList(data)
    }

    /**
     * Filters the data based on the provided query.
     * @param query The search query to filter states.
     */
    fun filterData(query: String) {
        adapter.filter(query)
    }

    /**
     * Gets the currently filtered list of states.
     * @return The filtered list of State objects.
     */
    fun getFilteredList() = adapter.getFilteredList()

    /**
     * Called when the view created by onCreateView(LayoutInflater, ViewGroup, Bundle) has been detached from the fragment.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}