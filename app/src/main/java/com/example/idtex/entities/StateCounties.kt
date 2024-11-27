package com.example.idtex.entities

import com.google.gson.annotations.SerializedName

data class StateCounties(
    @field:SerializedName("state")
    val state: String? = null,

    @field:SerializedName("counties")
    val counties: List<String>? = null
)
