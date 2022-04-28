package com.example.fyta_test_assignment.model.response.search

import com.google.gson.annotations.SerializedName


data class Species (

    @SerializedName("scientificNameWithoutAuthor" ) var scientificNameWithoutAuthor : String?           = null,
    @SerializedName("scientificNameAuthorship"    ) var scientificNameAuthorship    : String?           = null,
    @SerializedName("genus"                       ) var genus                       : Genus?            = Genus(),
    @SerializedName("family"                      ) var family                      : Family?           = Family(),
    @SerializedName("commonNames"                 ) var commonNames                 : ArrayList<String> = arrayListOf(),
    @SerializedName("scientificName"              ) var scientificName              : String?           = null

)