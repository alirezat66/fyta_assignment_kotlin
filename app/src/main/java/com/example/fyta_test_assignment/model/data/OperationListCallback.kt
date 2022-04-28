package com.example.fyta_test_assignment.model.data

import com.example.fyta_test_assignment.model.response.search.SearchModel

interface OperationListCallback<T> {
    fun onSuccess(data: List<SearchModel>?)
    fun onError(error: String?)
}