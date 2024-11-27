package com.example.idtex.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.idtex.databinding.ActivityMainBinding
import com.example.idtex.repositories.StateRepository
import com.example.idtex.viewmodels.StateViewModel
import com.example.idtex.viewmodels.StateViewModelFactory

/**
 * MainActivity is the entry point for the UI, responsible for displaying state and county data.
 */
class MainActivity : AppCompatActivity() {
    /**
     * ViewModel instance to handle state-related data operations.
     */
    private val stateViewModel: StateViewModel by viewModels { StateViewModelFactory(StateRepository()) }

    /**
     * ViewBinding instance to bind the UI components.
     */
    private lateinit var binding: ActivityMainBinding

    /**
     * Called when the activity is first created.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Fetch the States
        stateViewModel.fetchStates()

        // Fetch the Counties
        stateViewModel.fetchCountyDetailsByState()
    }
}
