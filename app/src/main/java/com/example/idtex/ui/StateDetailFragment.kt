package com.example.idtex.ui

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.idtex.R
import com.example.idtex.databinding.FragmentStateDetailBinding
import com.example.idtex.entities.State
import com.example.idtex.repositories.StateRepository
import com.example.idtex.utils.DATA
import com.example.idtex.utils.SELECTED_STATE
import com.example.idtex.utils.fromHtml
import com.example.idtex.viewmodels.StateViewModel
import com.example.idtex.viewmodels.StateViewModelFactory
import kotlin.getValue


/**
 * Fragment responsible for displaying details about a specific state, including
 * its population and counties.
 */
class StateDetailFragment : Fragment() {

    private var _binding: FragmentStateDetailBinding? = null
    private val binding get() = _binding!!

    /**
     * ViewModel instance to handle data operations related to states and counties.
     */
    private val stateViewModel: StateViewModel by activityViewModels {
        StateViewModelFactory(
            StateRepository()
        )
    }

    /**
     * Called to have the fragment instantiate its user interface view.
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return Return the View for the fragment's UI.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStateDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Called immediately after onCreateView has returned, but before any saved state has been restored in to the view.
     * @param view The View returned by onCreateView.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     */
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Initialize views with data from arguments
        if (arguments?.containsKey(DATA) == true) {
            initViews(arguments)
        }

        // Initialize views with result arguments
        requireActivity().supportFragmentManager.setFragmentResultListener(
            SELECTED_STATE, this
        ) { key, bundle ->
            initViews(bundle)
        }

    }

    /**
     * Initialize views with information from the given bundle.
     * @param bundle Bundle containing the state data.
     */
    @SuppressLint("SetTextI18n")
    private fun initViews(bundle: Bundle?) {
        // handel null bundle
        if (bundle == null) return

        val state =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) bundle.getSerializable(
                DATA, State::class.java
            )
            else bundle.getSerializable(DATA) as State

        state?.let {
            binding.stateName.text =
                requireContext().getString(com.example.idtex.R.string.state, it.state).fromHtml()
            binding.population.text = requireContext().getString(
                com.example.idtex.R.string.population_level,
                it.population.toString()
            ).fromHtml()
            binding.tvCounties.text = requireContext().getString(
                com.example.idtex.R.string.counties,
                it.counties.toString()
            ).fromHtml()


            stateViewModel.countyResponse.observe(viewLifecycleOwner) { response ->
                if (response.isSuccessful) {
                    response.body()?.let {
                        val counties = it.find { it.state == state.state }?.counties.orEmpty()
                        binding.rvCounties.layoutManager =
                            androidx.recyclerview.widget.LinearLayoutManager(context)
                        binding.rvCounties.adapter = CountyAdapter().apply {
                            submitList(counties)
                        }
                        binding.totalCounties.text =
                            getString(
                                R.string.state_counties,
                                (state.counties == counties.size).toString()
                            )
                    }
                } else {
                    Toast.makeText(
                        requireActivity(),
                        response.errorBody()?.string(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    /**
     * Called when the view created by onCreateView has been detached from the fragment.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}