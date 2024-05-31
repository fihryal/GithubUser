package com.faqiy.githubuser.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.faqiy.githubuser.data.modal.ResponseUser

@Database(entities = [ResponseUser.Item::class], version = 1, exportSchema = false)
abstract class AppDB : RoomDatabase() {
    abstract fun userDao():UserDao
}