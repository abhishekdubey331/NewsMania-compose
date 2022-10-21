package com.clean.newsapp.data.local.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.clean.newsapp.data.local.entities.NewsItemEntity

@Dao
interface NewsDao {

    @Query("SELECT * FROM newsItem")
    fun getNewsFeed(): List<NewsItemEntity>

    @Query("SELECT * FROM newsItem where id=:id")
    fun getNewsItemById(id: Int): NewsItemEntity

    /**
     * Inserts or updates [entities] in the db under the specified primary keys
     *  Source - https://github.com/android/nowinandroid/blob/7e6cb46436abd4895d2e11ff2eb6478cb9cca4dc/core/database/src/main/java/com/google/samples/apps/nowinandroid/core/database/dao/TopicDao.kt#L64
     */
    @Upsert
    suspend fun upsertNewsFeed(entities: List<NewsItemEntity>)

    /***
     *  Fun to check if cached data is available before accessing
     */
    @Query("SELECT (SELECT COUNT(*) FROM newsItem) != 0")
    suspend fun isCachedFeedAvailable(): Boolean
}
