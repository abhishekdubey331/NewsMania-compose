package com.clean.newsapp.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.clean.newsapp.data.local.entities.NewsItemEntity

@Database(entities = [NewsItemEntity::class], version = 1, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}
