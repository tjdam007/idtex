package com.example.idtex.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Data class representing a state with information about its name, number of counties, and population.
 *
 * @property state The name of the state.
 * @property counties The number of counties in the state.
 * @property population The population of the state.
 */
data class State(
    @field:SerializedName("name")
    val state: String? = null,

    @field:SerializedName("county_count")
    val counties: Int? = null,

    @field:SerializedName("population")
    val population: Int? = null
) : Serializable
