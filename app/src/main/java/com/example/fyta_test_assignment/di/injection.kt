package com.example.fyta_test_assignment.di

import com.emedinaa.kotlinmvvm.data.ApiClient
import com.example.fyta_test_assignment.model.data.repository.SearchDataSource
import com.example.fyta_test_assignment.model.data.repository.SearchRemoteDataSource
import com.example.fyta_test_assignment.model.data.repository.SearchRepository
import com.example.fyta_test_assignment.viewmodel.search.SearchViewModelFactory

object Injection {
    private var searchDataSource: SearchDataSource? = null
    private var searchRepository: SearchRepository? = null
    private var searchViewModelFactory: SearchViewModelFactory? = null

    private fun createSearchDataSource(): SearchDataSource {
        val dataSource = SearchRemoteDataSource(ApiClient)
        searchDataSource = dataSource
        return dataSource
    }

    private fun createSearchRepository(): SearchRepository {
        val repository = SearchRepository(provideDataSource())
        searchRepository = repository
        return repository
    }

    private fun createFactory(): SearchViewModelFactory {
        val factory = SearchViewModelFactory(providerRepository())
        searchViewModelFactory = factory
        return factory
    }

    private fun provideDataSource() = searchDataSource ?: createSearchDataSource()
    private fun providerRepository() = searchRepository ?: createSearchRepository()

    fun provideViewModelFactory() = searchViewModelFactory ?: createFactory()

    fun destroy() {
        searchDataSource = null
        searchRepository = null
        searchViewModelFactory = null
    }
}