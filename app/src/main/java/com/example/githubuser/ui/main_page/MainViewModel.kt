package com.example.githubuser.ui.main_page

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.data.response.GithubResponse
import com.example.githubuser.data.response.ItemsItem
import com.example.githubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _itemsitem = MutableLiveData<List<ItemsItem>>()
    val itemsitem: LiveData<List<ItemsItem>> = _itemsitem

    private val _isloading = MutableLiveData<Boolean>()
    val isloading: LiveData<Boolean> = _isloading

    companion object {
        private const val TAG = "MainViewModel"
    }

    init {
        searchUserGithub("a")
    }

    fun searchUserGithub(q: String) {
        _isloading.value = true
        val client = ApiConfig.getApiService().getUserGithub(q)
        client.enqueue(object : Callback<GithubResponse>{
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isloading.value = false
                if(response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _itemsitem.value = responseBody.items
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isloading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}