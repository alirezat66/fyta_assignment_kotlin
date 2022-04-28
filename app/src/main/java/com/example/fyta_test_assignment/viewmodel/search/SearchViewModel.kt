package com.example.fyta_test_assignment.viewmodel.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fyta_test_assignment.model.data.OperationListCallback
import com.example.fyta_test_assignment.model.data.repository.SearchRepository
import com.example.fyta_test_assignment.model.response.search.SearchModel
import com.example.fyta_test_assignment.model.response.search.SearchResult
import java.io.File

class SearchViewModel(private val repository: SearchRepository) : ViewModel() {

    private val _results = MutableLiveData<List<SearchModel>>().apply { value = emptyList() }
    val results: LiveData<List<SearchModel>> = _results

    private val _isViewLoading = MutableLiveData<Boolean>()
    val isViewLoading: LiveData<Boolean> = _isViewLoading

    private val _onMessageError = MutableLiveData<Any>()
    val onMessageError: LiveData<Any> = _onMessageError

    private val _isEmptyList = MutableLiveData<Boolean>()
    val isEmptyList: LiveData<Boolean> = _isEmptyList

    /*
    If you require that the data be loaded only once, you can consider calling the method
    "search(file)" on constructor. Also, if you rotate the screen, the service will not be called.
    init {
        //search(file)
    }
     */

    fun search(file: File) {
        _isViewLoading.value = true
        repository.searchByImage(object : OperationListCallback<SearchResult> {
            override fun onError(error: String?) {
                _isViewLoading.value = false
                _onMessageError.value = error!!
            }

            override fun onSuccess(data: List<SearchModel>?) {
                _isViewLoading.value = false
                if (data.isNullOrEmpty()) {
                    _isEmptyList.value = true

                } else {
                    _results.value = data
                }
            }
        }, file)
    }


}