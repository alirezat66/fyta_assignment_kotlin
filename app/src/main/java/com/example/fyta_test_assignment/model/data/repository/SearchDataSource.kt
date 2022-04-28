package com.example.fyta_test_assignment.model.data.repository

import com.example.fyta_test_assignment.model.data.OperationListCallback
import com.example.fyta_test_assignment.model.response.search.SearchResult
import java.io.File

interface SearchDataSource {
    fun searchByImage(callback: OperationListCallback<SearchResult>,file : File)
}