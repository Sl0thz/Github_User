package com.example.githubuser.ui.user_detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.data.response.DetailUserResponse
import com.example.githubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailViewModel : ViewModel() {

    private val _isloading = MutableLiveData<Boolean>()
    val isloading: LiveData<Boolean> = _isloading

    private val _userdetail = MutableLiveData<DetailUserResponse>()
    val userdetail: LiveData<DetailUserResponse> = _userdetail

    companion object {
        private const val TAG = "UserDetailViewModel"
    }

    fun userDetail(username: String) {
        _isloading.value = true
        val client = ApiConfig.getApiService().getUserDetail(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isloading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _userdetail.value = response.body()
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isloading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}