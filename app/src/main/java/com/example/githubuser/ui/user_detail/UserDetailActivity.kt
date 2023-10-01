package com.example.githubuser.ui.user_detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.githubuser.R
import com.example.githubuser.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayoutMediator

@Suppress("NAME_SHADOWING")
class UserDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: UserDetailViewModel

    companion object {
        const val USER_DETAIL = "USER_DETAIL"
        private val TAB_LAYOUT = intArrayOf(R.string.followers, R.string.following)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detail = intent.getStringExtra(USER_DETAIL)
        val bundle = Bundle()
        val userdetail = detail.toString()
        val sectionsPagerAdapter = SectionsPagerAdapter(this@UserDetailActivity, userdetail)
        bundle.putString(USER_DETAIL, detail)

        detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[UserDetailViewModel::class.java]
        detailViewModel.userDetail(detail.toString())
        detailViewModel.isloading.observe(this) {
            showLoading(it)
        }



        detailViewModel.userdetail.observe(this) {
            if (it != null) {
                binding.apply {
                    tvDetail.text = it.name
                    Username.text = it.login
                    Followers.text = "${it.followers} Followers"
                    Following.text = "${it.following} Following"
                    Glide.with(this@UserDetailActivity)
                        .load(it.avatarUrl)
                        .into(binding.ivDetail)
                }
            }
        }

        val pagerAdapter = binding.viewPager
        pagerAdapter.adapter = sectionsPagerAdapter
        val tabLayout = binding.tabLayout
        TabLayoutMediator(tabLayout, pagerAdapter) { tabLayout, position ->
            tabLayout.text = resources.getString(TAB_LAYOUT[position])
        }.attach()
        supportActionBar?.elevation
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}