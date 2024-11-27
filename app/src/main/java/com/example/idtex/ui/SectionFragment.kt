package com.example.idtex.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.idtex.R
import com.example.idtex.databinding.FragmentSectionBinding
import com.example.idtex.repositories.StateRepository
import com.example.idtex.utils.DATA
import com.example.idtex.viewmodels.StateViewModel
import com.example.idtex.viewmodels.StateViewModelFactory
import kotlin.getValue

/**
 * A fragment representing a section of the app with a list of states and a state detail view.
 */
class SectionFragment : Fragment() {
    private var _binding: FragmentSectionBinding? = null
    private val binding get() = _binding!!

    private val stateViewModel: StateViewModel by activityViewModels {
        StateViewModelFactory(
            StateRepository()
        )
    }

    /**
     * Called to have the fragment instantiate its user interface view.
     * @param inflater LayoutInflater: The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container ViewGroup?: If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState Bundle?: If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return View? The created view or null if the fragment does not have a UI.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Called immediately after {@link #onCreateView} has returned, but before any saved state has been restored in to the view.
     * @param view View: The View returned by {@link #onCreateView}.
     * @param savedInstanceState Bundle?: If non-null, this fragment is being re-constructed from a previous saved state as given here.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentSimpleList =
            childFragmentManager.findFragmentById(R.id.containerList) as StateListFragment
        val fragmentFilterList =
            childFragmentManager.findFragmentById(R.id.containerFilterList) as StateListFragment
        val fragmentDetails =
            childFragmentManager.findFragmentById(R.id.containerDetails) as StateDetailFragment

        // Observe data response
        stateViewModel.states.observe(viewLifecycleOwner) { response ->
            if (response.isSuccessful) {
                response.body()?.let {
                    fragmentSimpleList.setData(it)
                    fragmentFilterList.setData(it)
                }
            } else {
                Toast.makeText(
                    requireActivity(),
                    response.errorBody()?.string(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.filterEditText.doAfterTextChanged { text ->
            fragmentFilterList.filterData(text.toString())
        }

        binding.buttonSecondScreen.setOnClickListener {
            val items = fragmentFilterList.getFilteredList()
            if (items.size == 1) {
                // Clear the filter
                binding.filterEditText.setText("")

                // Replace the current fragment with the new fragment
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.rootContainer, StateDetailFragment().apply {
                        arguments = bundleOf(DATA to items[0])
                    })
                    .addToBackStack(null)  // Add this line to enable back navigation
                    .commit()

            } else {
                val text = when {
                    items.isEmpty() -> getString(R.string.no_state_available)
                    else -> getString(R.string.select_only_one_state)
                }
                Toast.makeText(requireActivity(), text, Toast.LENGTH_SHORT).show()
            }
        }

    }

    /**
     * Called when the view previously created by {@link #onCreateView} has been detached from the fragment.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}