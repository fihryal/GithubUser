package com.faqiy.githubuser.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.faqiy.githubuser.data.api.ApiClient
import com.faqiy.githubuser.data.local.SettingPreferences
import com.faqiy.githubuser.utils.Result
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainViewModel(private val preferences : SettingPreferences) : ViewModel() {

    val resultUser = MutableLiveData<Result>()

    fun getTheme() = preferences.getThemeSetting().asLiveData()

    fun getUsers() {
        viewModelScope.launch {
            flow {
                val response = ApiClient
                    .githubService
                    .getUser()

                emit(response)
            }.onStart {
                resultUser.value = Result.Loading (true)
            }.onCompletion {
                resultUser.value = Result.Loading (false)
            }.catch {
                it.printStackTrace()
                resultUser.value = Result.Error(it)
            }.collect {
                resultUser.value = Result.Success(it)
            }
        }
    }

    fun getUsers(username: String) {
        viewModelScope.launch {
            flow {
                val response = ApiClient
                    .githubService
                    .searchUser(mapOf(
                        "q" to username,
                        "per_page" to 10
                    ))

                emit(response)
            }.onStart {
                resultUser.value = Result.Loading(true)
            }.onCompletion {
                resultUser.value = Result.Loading(false)
            }.catch {
                it.printStackTrace()
                resultUser.value = Result.Error(it)
            }.collect {
                resultUser.value = Result.Success(it.items)
            }

        }
    }

    class Factory (private val preferences: SettingPreferences) :
            ViewModelProvider.NewInstanceFactory(){
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            MainViewModel(preferences) as T
            }
}