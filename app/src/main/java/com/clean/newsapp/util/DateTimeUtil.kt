package com.clean.newsapp.util

import com.clean.newsapp.common.Constants
import java.text.SimpleDateFormat
import java.util.*

class DateTimeUtil {

    /***
     *  Returns human readable time
     */
    fun uiTimeFormat(publishedAt: String): String = try {
        val formatter = SimpleDateFormat(Constants.SERVER_TIME_FORMAT, Locale.ENGLISH)
        val mDate = formatter.parse(publishedAt)
        if (mDate != null) {
            SimpleDateFormat(Constants.UI_TIME_FORMAT, Locale.ENGLISH).format(mDate)
        } else {
            publishedAt
        }
    } catch (e: Exception) {
        publishedAt
    }
}
