package com.faqiy.githubuser.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.faqiy.githubuser.data.modal.ResponseUser

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: ResponseUser.Item)

    @Query("SELECT * FROM user")
    fun loadAll(): LiveData<MutableList<ResponseUser.Item>>

    @Query("SELECT * FROM user WHERE id LIKE :id LIMIT 1")
    fun findById(id : Int) : ResponseUser.Item

    @Delete
    fun delete(user: ResponseUser.Item)
}