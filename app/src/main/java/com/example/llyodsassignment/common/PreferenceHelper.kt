package com.example.llyodsassignment.common

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import org.jetbrains.annotations.VisibleForTesting
import javax.inject.Inject

class PreferenceHelper @Inject constructor(@ApplicationContext context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        @VisibleForTesting
        internal const val PREFS_NAME = "cat_images_prefs"
        @VisibleForTesting
        internal const val LAST_REFRESH_TIME_KEY = "last_refresh_time"
    }

    fun saveLastRefreshTime(time: Long) {
        sharedPreferences.edit().putLong(LAST_REFRESH_TIME_KEY, time).apply()
    }

    fun getLastRefreshTime(): Long {
        return sharedPreferences.getLong(LAST_REFRESH_TIME_KEY, 0L)
    }
}