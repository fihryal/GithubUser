package com.faqiy.githubuser.detail

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.widget.Adapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import coil.load
import coil.transform.CircleCropTransformation
import com.faqiy.githubuser.R
import com.faqiy.githubuser.adapter.DetailAdapter
import com.faqiy.githubuser.data.local.ModulDB
import com.faqiy.githubuser.data.modal.ResponseDetailUser
import com.faqiy.githubuser.data.modal.ResponseUser
import com.faqiy.githubuser.databinding.ActivityDetailBinding
import com.faqiy.githubuser.detail.fragment.FollowFragment
import com.faqiy.githubuser.utils.Result
import com.faqiy.githubuser.viewmodel.DetailViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator


class DetailActivity : AppCompatActivity() {

    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding as ActivityDetailBinding

    private val viewModel by viewModels<DetailViewModel> {
        DetailViewModel.Factory(ModulDB(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val item = intent.getParcelableExtra<ResponseUser.Item>("item")
        val username = item?.login ?: ""

        viewModel.resultDetailUser.observe(this) {
            when (it) {
                is Result.Success<*> -> {
                    val user = it.data as ResponseDetailUser
                    binding.apply {
                        profileImage.load(user.avatar_url) {
                            transformations(CircleCropTransformation())
                        }
                        tvName.text = user.login
                        tvUsername.text = "@" + user.name
                        tvFollowers.text = user.followers.toString() + " " + "followers"
                        titik.text = "*"
                        tvFollowing.text = user.following.toString() + " " + "following"
                    }
                }

                is Result.Error -> {
                    Toast.makeText(this, it.exception.message.toString(), Toast.LENGTH_SHORT).show()
                }

                is Result.Loading -> {
                    binding.progressBar.isVisible = it.isLoading
                }
            }
        }
        viewModel.getDetailUsers(username)

        val fragment = mutableListOf<Fragment>(
            FollowFragment.newInstance(FollowFragment.FOLLOWERS),
            FollowFragment.newInstance(FollowFragment.FOLLOWING)
        )

        val titleFragment = mutableListOf(
            getString(R.string.follower),
            getString(R.string.following)
        )

        val adapter = DetailAdapter(this, fragment)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, posisi ->
            tab.text = titleFragment[posisi]
        }.attach()

        if (savedInstanceState == null) {
            viewModel.getFollowers(username)
        }

        viewModel.resultIsFavorite.observe(this){
            binding.btnFavorite.changeIconColor(R.color.red)
        }
        viewModel.resultNotFavorite.observe(this){
            binding.btnFavorite.changeIconColor(R.color.black)
        }

        binding.btnFavorite.setOnClickListener {
            viewModel.getFavorite(item)
        }

        viewModel.findFaforite(item?.id ?: 0) {
            binding.btnFavorite.changeIconColor(R.color.red)
        }

        binding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {

                if (tab?.position == 0) {
                    viewModel.getFollowers(username)
                } else {
                    viewModel.getFollowing(username)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }


        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

fun FloatingActionButton.changeIconColor(@ColorRes color: Int) {
    imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this.context, color))
}
