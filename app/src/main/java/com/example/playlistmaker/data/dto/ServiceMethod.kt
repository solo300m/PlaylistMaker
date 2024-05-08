package com.example.playlistmaker.data.dto

import android.content.Context
import android.util.TypedValue

interface ServiceMethod {
    fun dpToPx(dp: Float, context: Context): Int
    fun change512(str: String, strTo: String): String
}
