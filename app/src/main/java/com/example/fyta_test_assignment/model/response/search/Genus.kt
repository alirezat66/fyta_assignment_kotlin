package com.example.fyta_test_assignment.model.response.search

import com.google.gson.annotations.SerializedName


data class Genus (

  @SerializedName("scientificNameWithoutAuthor" ) var scientificNameWithoutAuthor : String? = null,
  @SerializedName("scientificNameAuthorship"    ) var scientificNameAuthorship    : String? = null,
  @SerializedName("scientificName"              ) var scientificName              : String? = null

)