package com.example.fyta_test_assignment.model.response.search

import com.google.gson.annotations.SerializedName


data class SearchResult (


  @SerializedName("results") var results : ArrayList<SearchModel> = arrayListOf(),


)