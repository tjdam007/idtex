package com.example.idtex.viewmodels

import androidx.lifecycle.*
import com.example.idtex.entities.State
import com.example.idtex.entities.StateCounties
import com.example.idtex.repositories.StateRepository
import kotlinx.coroutines.launch
import retrofit2.Response


/**
 * ViewModel for managing and preparing [State] and [StateCounties] data for the UI.
 *
 * @param repository The data repository used to fetch state and county details.
 */
class StateViewModel(private val repository: StateRepository) : ViewModel() {

    private val _states = MutableLiveData<Response<List<State>>>()
    val states: LiveData<Response<List<State>>> = _states

    private val _countyResponse = MutableLiveData<Response<List<StateCounties>>>()
    val countyResponse: LiveData<Response<List<StateCounties>>> = _countyResponse

    /**
     * Fetches the list of states from the repository and posts the response to [_states].
     */
    fun fetchStates() {
        viewModelScope.launch {
            val response = repository.getStates()
            _states.postValue(response)
        }
    }

    /**
     * Fetches county details by state from the repository and posts the response to [_countyResponse].
     */
    fun fetchCountyDetailsByState() {
        viewModelScope.launch {
            val response = repository.fetchCountyDetailsByState()
            _countyResponse.postValue(response)
        }
    }

    private val _selectedState = MutableLiveData<State?>()
    val selectedState: LiveData<State?> get() = _selectedState

    fun selectState(state: State?) {
        _selectedState.value = state
    }
}

/**
 * Factory class to instantiate [StateViewModel] with its required [StateRepository] dependency.
 *
 * @param repository The data repository used to fetch state and county details.
 */
class StateViewModelFactory(private val repository: StateRepository) : ViewModelProvider.Factory {

    /**
     * Creates a new instance of the specified [ViewModel] class.
     *
     * @param modelClass The class of the ViewModel to be instantiated.
     * @return A newly created ViewModel instance.
     * @throws IllegalArgumentException if the specified model class is not [StateViewModel].
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StateViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") return StateViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}