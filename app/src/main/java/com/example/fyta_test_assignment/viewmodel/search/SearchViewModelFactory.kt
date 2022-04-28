package com.example.fyta_test_assignment.viewmodel.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fyta_test_assignment.model.data.repository.SearchRepository

class SearchViewModelFactory(private val repository: SearchRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchViewModel(repository) as T
    }
}