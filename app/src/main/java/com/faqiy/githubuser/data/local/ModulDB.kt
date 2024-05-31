package com.faqiy.githubuser.data.local

import android.content.Context
import androidx.room.Room

class ModulDB(private val context : Context) {
    private val db = Room.databaseBuilder(context,AppDB::class.java , name = "user.db")
        .allowMainThreadQueries()
        .build()

    val userDao = db.userDao()
}