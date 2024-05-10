package com.example.playlistmaker.data.dto

import android.content.Context

interface ServiceMethod {
    fun dpToPx(dp: Float, context: Context): Int
    fun setSizePicture512(str: String, strTo: String): String
}
