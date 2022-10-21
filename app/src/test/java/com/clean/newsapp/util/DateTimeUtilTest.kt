package com.clean.newsapp.util

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class DateTimeUtilTest {

    private lateinit var dateTimeUtil: DateTimeUtil

    @Before
    fun setup() {
        dateTimeUtil = DateTimeUtil()
    }

    @Test
    fun `uiTimeFormat returns correct ui formatted time`() {
        // Given
        val publishedTime = "2022-10-11T11:00:11Z"
        val expectedFormattedTime = "Tue, 11 Oct 2022 at 11:00 AM"

        // Invoke
        val result = dateTimeUtil.uiTimeFormat(publishedTime)

        // Then
        assertThat(result).isEqualTo(expectedFormattedTime)
    }

    @Test
    fun `uiTimeFormat returns published time without change if parse exception occurs ui formatted time`() {
        // Given
        val publishedTime = "any_random_string"

        // Invoke
        val result = dateTimeUtil.uiTimeFormat(publishedTime)

        // Then
        assertThat(result).isEqualTo(publishedTime)
    }
}