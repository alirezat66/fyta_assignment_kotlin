package com.example.fyta_test_assignment.model.response.search

import com.google.gson.annotations.SerializedName


data class Query (

  @SerializedName("project"              ) var project              : String?           = null,
  @SerializedName("images"               ) var images               : ArrayList<String> = arrayListOf(),
  @SerializedName("organs"               ) var organs               : ArrayList<String> = arrayListOf(),
  @SerializedName("includeRelatedImages" ) var includeRelatedImages : Boolean?          = null

)