package com.faqiy.githubuser.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.faqiy.githubuser.data.local.ModulDB

class FavoriteViewModel (private val ModulDb : ModulDB) : ViewModel() {

    fun getUserFavorite () = ModulDb.userDao.loadAll()

    class Factory(private val db: ModulDB) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T = FavoriteViewModel(db) as T
    }
}