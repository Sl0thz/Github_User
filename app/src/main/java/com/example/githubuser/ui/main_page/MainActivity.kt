package com.example.githubuser.ui.main_page

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.data.response.ItemsItem
import com.example.githubuser.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.rvItems.layoutManager = LinearLayoutManager(this)
        setContentView(binding.root)

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, _, _ ->
                    searchView.hide()
                    if(textView.text.isNotEmpty()) {
                        mainViewModel.searchUserGithub(textView.text.toString())
                        searchBar.text = searchView.text
                    } else {
                        Toast.makeText(this@MainActivity, "Cannot be Empty", Toast.LENGTH_SHORT).show()
                    }
                    false
                }
        }
        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MainViewModel::class.java]
        mainViewModel.itemsitem.observe(this) { itemsItems ->
            setUserData(itemsItems)
        }
        mainViewModel.isloading.observe(this){
            showLoading(it)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUserData(userData: List<ItemsItem>){
        val adapter = UserAdapter().apply {
            this.notifyDataSetChanged()
            submitList(userData)
        }
        binding.rvItems.adapter = adapter
        }

    private fun showLoading(isLoading: Boolean) {
        if(isLoading){
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}