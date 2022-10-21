package com.clean.newsapp.di

import android.content.Context
import androidx.room.Room
import com.clean.newsapp.common.Constants
import com.clean.newsapp.data.local.db.NewsDao
import com.clean.newsapp.data.local.db.NewsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideNewsDao(
        @ApplicationContext context: Context
    ): NewsDao = Room.databaseBuilder(
        context, NewsDatabase::class.java,
        Constants.DB_NAME
    ).fallbackToDestructiveMigration()
        .build().newsDao()
}
