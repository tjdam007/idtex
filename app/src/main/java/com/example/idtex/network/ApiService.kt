package com.example.idtex.network

import com.example.idtex.entities.State
import com.example.idtex.entities.StateCounties
import retrofit2.Response
import retrofit2.http.GET

/**
 * ApiInterface defines the REST API endpoints for fetching states and county details.
 */
interface ApiInterface {

    /**
     * Fetches a list of states.
     *
     * @return a Response object containing a list of State objects.
     */
    @GET("storage/v1/object/public/releasefiles/states.json")
    suspend fun getStates(): Response<List<State>>

    /**
     * Fetches county details for each state.
     *
     * @return a Response object containing a list of StateCounties objects.
     */
    @GET("storage/v1/object/public/releasefiles/state_counties.json")
    suspend fun fetchCountyDetailsByState(): Response<List<StateCounties>>
}



