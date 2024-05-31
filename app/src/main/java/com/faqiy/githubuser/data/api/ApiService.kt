package com.faqiy.githubuser.data.api

import com.faqiy.githubuser.BuildConfig
import com.faqiy.githubuser.data.modal.ResponseDetailUser
import com.faqiy.githubuser.data.modal.ResponseUser
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ApiService {

    @GET("users")
    suspend fun getUser (
        @Header("Authorization")
        authorization: String = BuildConfig.TOKEN
    ) : MutableList<ResponseUser.Item>

    @GET("users/{username}")
    suspend fun getDetailUser (
        @Path("username")username : String,
        @Header("Authorization")
    authorization: String = BuildConfig.TOKEN) : ResponseDetailUser

    @GET("users/{username}/followers")
    suspend fun getfollowersUser (
        @Path("username")username : String,
        @Header("Authorization")
    authorization: String = BuildConfig.TOKEN) : MutableList<ResponseUser.Item>

    @GET("users/{username}/following")
    suspend fun getfollowingUser (
        @Path("username")username : String,
        @Header("Authorization")
        authorization: String = BuildConfig.TOKEN) : MutableList<ResponseUser.Item>

    @JvmSuppressWildcards
    @GET("search/users")
    suspend fun searchUser (
        @QueryMap params : Map<String, Any>,
        @Header("Authorization")
        authorization: String = BuildConfig.TOKEN) : ResponseUser
}