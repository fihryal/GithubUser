package com.faqiy.githubuser.detail.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.faqiy.githubuser.R
import com.faqiy.githubuser.adapter.UserAdapter
import com.faqiy.githubuser.data.local.ModulDB
import com.faqiy.githubuser.databinding.ActivityFavoriteBinding
import com.faqiy.githubuser.detail.DetailActivity
import com.faqiy.githubuser.viewmodel.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {
    private var _binding: ActivityFavoriteBinding? = null
    private val binding get() = _binding as ActivityFavoriteBinding

    private val adapter by lazy {
        UserAdapter { user ->
            Intent(this, DetailActivity::class.java).apply {
                putExtra("item", user)
                startActivity(this)
            }
        }
    }

    private val viewModel by viewModels<FavoriteViewModel> {
        FavoriteViewModel.Factory(ModulDB(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.rvFavorite.layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.adapter = adapter

        viewModel.getUserFavorite().observe(this) { favoriteList ->
            if (favoriteList.isNullOrEmpty()) {
                binding.textFavorite.visibility = android.view.View.VISIBLE
                binding.rvFavorite.visibility = android.view.View.GONE
            } else {
                binding.textFavorite.visibility = android.view.View.GONE
                binding.rvFavorite.visibility = android.view.View.VISIBLE
                adapter.setData(favoriteList)
            }
        }
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