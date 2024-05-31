package com.faqiy.githubuser.detail.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.faqiy.githubuser.R
import com.faqiy.githubuser.adapter.UserAdapter
import com.faqiy.githubuser.data.modal.ResponseUser
import com.faqiy.githubuser.databinding.FragmentFollowBinding
import com.faqiy.githubuser.utils.Result
import com.faqiy.githubuser.viewmodel.DetailViewModel


class FollowFragment : Fragment() {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding as FragmentFollowBinding

    private val adapter by lazy {
        UserAdapter {

        }
    }

    private val viewModel by activityViewModels<DetailViewModel>()
    var type = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFollowBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvFollow.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = this@FollowFragment.adapter
        }

        when (type) {
            FOLLOWERS -> {
                viewModel.resultFollowUser.observe(viewLifecycleOwner, this::manageResultFollow)
                viewModel.getFollowers(arguments?.getString("username") ?: "")
            }

            FOLLOWING -> {
                viewModel.resultFollowingUser.observe(viewLifecycleOwner, this::manageResultFollow)
                viewModel.getFollowing(arguments?.getString("username") ?: "")
            }
        }

    }

    private fun manageResultFollow(state: Result) {
        when (state) {
            is Result.Success<*> -> {
                adapter.setData(state.data as MutableList<ResponseUser.Item>)
            }

            is Result.Error -> {
                Toast.makeText(
                    requireContext(),
                    state.exception.message.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }

            is Result.Loading -> {
                binding.progressBar.isVisible = state.isLoading
            }
        }
    }

    companion object {
        const val FOLLOWING = 100
        const val FOLLOWERS = 102
        fun newInstance(type: Int) = FollowFragment()
            .apply {
                this.type = type
            }
    }
}