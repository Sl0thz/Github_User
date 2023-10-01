package com.example.githubuser.ui.user_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.data.response.ItemsItem
import com.example.githubuser.databinding.FragmentFollowBinding
import com.example.githubuser.ui.main_page.UserAdapter

class FollowsFragment : Fragment() {

    private lateinit var binding: FragmentFollowBinding
    private lateinit var followsViewModel: FollowsViewModel

    override fun onCreateView(
        li: LayoutInflater,
        vg: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowBinding.inflate(li, vg, false)
        followsViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[FollowsViewModel::class.java]
        return binding.root
    }

    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(requireActivity())
        val position = arguments?.getInt(ARG_POSITION)
        val username = arguments?.getString(ARG_USERNAME)
        binding = FragmentFollowBinding.bind(view)
        binding.rvFollows.layoutManager = layoutManager
        binding.rvFollows.adapter = UserAdapter()

        if (position == 0 && username != null) {
            followsViewModel.setListFollowers(username)
            followsViewModel.listfollowers.observe(viewLifecycleOwner) {
                setDataUser(it)
            }
            followsViewModel.isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }
        } else if (position == 1 && username != null) {
            followsViewModel.setListFollowing(username)
            followsViewModel.listfollowing.observe((viewLifecycleOwner)) {
                setDataUser(it)
            }
            followsViewModel.isLoading.observe((viewLifecycleOwner)) {
                showLoading(it)
            }
        }
    }

    private fun setDataUser(userData: List<ItemsItem>) {
        val adapter = UserAdapter().apply {
            submitList(userData)
        }
        binding.rvFollows.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}