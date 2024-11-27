package com.example.idtex.repositories

import com.example.idtex.entities.State
import com.example.idtex.entities.StateCounties
import com.example.idtex.network.RetrofitInstance.api
import retrofit2.Response

/**
 * Repository for handling State data operations.
 */
class StateRepository {

    /**
     * Retrieves a list of states.
     *
     * @return A [Response] containing a list of [State] objects.
     */
    suspend fun getStates(): Response<List<State>> {
        return api.getStates()
    }

    /**
     * Fetches county details for a specific state.
     *
     * @return A [Response] containing a list of [StateCounties] objects.
     */
    suspend fun fetchCountyDetailsByState(): Response<List<StateCounties>> {
        return api.fetchCountyDetailsByState()
    }
}
