package com.faqiy.githubuser.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.faqiy.githubuser.data.api.ApiClient
import com.faqiy.githubuser.data.local.ModulDB
import com.faqiy.githubuser.data.modal.ResponseUser
import com.faqiy.githubuser.utils.Result
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class DetailViewModel(private val db: ModulDB) : ViewModel() {

    val resultDetailUser = MutableLiveData<Result>()
    val resultFollowUser = MutableLiveData<Result>()
    val resultFollowingUser = MutableLiveData<Result>()
    val resultIsFavorite = MutableLiveData<Boolean>()
    val resultNotFavorite = MutableLiveData<Boolean>()

    private var isFavorite = false

    fun getFavorite(item: ResponseUser.Item?) {
        viewModelScope.launch {
            item?.let {
                if (isFavorite) {
                    db.userDao.delete(item)
                    resultNotFavorite.value = true
                } else {
                    db.userDao.insert(item)
                    resultIsFavorite.value = true
                }
            }
        }
        isFavorite = !isFavorite
    }

    fun findFaforite(id: Int, listenFavorite: () -> Unit) {
        viewModelScope.launch {
            val user = db.userDao.findById(id)
            if (user != null) {
                listenFavorite()
                isFavorite = true
            }
        }
    }

    fun getDetailUsers(username: String) {
        viewModelScope.launch {
            flow {
                val response = ApiClient
                    .githubService
                    .getDetailUser(username)

                emit(response)
            }.onStart {
                resultDetailUser.value = Result.Loading(true)
            }.onCompletion {
                resultDetailUser.value = Result.Loading(false)
            }.catch {
                it.printStackTrace()
                resultDetailUser.value = Result.Error(it)
            }.collect {
                resultDetailUser.value = Result.Success(it)
            }

        }
    }

    fun getFollowers(username: String) {
        viewModelScope.launch {
            flow {
                val response = ApiClient
                    .githubService
                    .getfollowersUser(username)

                emit(response)
            }.onStart {
                resultFollowUser.value = Result.Loading(true)
            }.onCompletion {
                resultFollowUser.value = Result.Loading(false)
            }.catch {
                it.printStackTrace()
                resultFollowUser.value = Result.Error(it)
            }.collect {
                resultFollowUser.value = Result.Success(it)
            }

        }
    }

    fun getFollowing(username: String) {
        viewModelScope.launch {
            flow {
                val response = ApiClient
                    .githubService
                    .getfollowingUser(username)

                emit(response)
            }.onStart {
                resultFollowingUser.value = Result.Loading(true)
            }.onCompletion {
                resultFollowingUser.value = Result.Loading(false)
            }.catch {
                it.printStackTrace()
                resultFollowingUser.value = Result.Error(it)
            }.collect {
                resultFollowingUser.value = Result.Success(it)
            }

        }
    }

    class Factory(private val db: ModulDB) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T = DetailViewModel(db) as T
    }

}