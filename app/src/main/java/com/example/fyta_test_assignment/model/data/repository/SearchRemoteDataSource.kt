package com.example.fyta_test_assignment.model.data.repository

import com.emedinaa.kotlinmvvm.data.ApiClient
import com.example.fyta_test_assignment.model.data.OperationListCallback
import com.example.fyta_test_assignment.model.response.search.SearchResult
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class SearchRemoteDataSource(apiClient: ApiClient) : SearchDataSource{
    private var call: Call<SearchResult>? = null
    private val service = apiClient.build()
    override fun searchByImage(callback: OperationListCallback<SearchResult>, file: File) {
        val reqBody: RequestBody =
            RequestBody.create(MediaType.parse("image/*"), file)
        val partImage =
            MultipartBody.Part.createFormData("images", file.name, reqBody)
        val list = ArrayList<MultipartBody.Part>()
        list.add(partImage);
        call = service?.searchByImage(images= list);
        call?.enqueue(object : Callback<SearchResult> {
            override fun onFailure(call: Call<SearchResult>, t: Throwable) {
                callback.onError(t.message)
            }

            override fun onResponse(
                call: Call<SearchResult>,
                response: Response<SearchResult>
            ) {
                if(response.body()!=null){
                    response.body()?.let {
                        if (response.isSuccessful) {
                            callback.onSuccess(it.results)
                        } else {
                            callback.onError(response.message())
                        }
                    }
                }else{
                    callback.onError(response.message())
                }


            }
        })
    }
}