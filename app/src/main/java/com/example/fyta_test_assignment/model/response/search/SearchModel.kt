package com.example.fyta_test_assignment.model.response.search

import com.google.gson.annotations.SerializedName


data class SearchModel (

    @SerializedName("score"   ) var score   : Double?  = null,
    @SerializedName("species" ) var species : Species? = Species(),
    @SerializedName("gbif"    ) var gbif    : Gbif?    = Gbif()

)