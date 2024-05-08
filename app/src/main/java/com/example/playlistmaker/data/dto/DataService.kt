package com.example.playlistmaker.data.dto

import android.content.Context
import android.util.TypedValue

class DataService:ServiceMethod {
    override fun dpToPx(dp: Float, context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics
        ).toInt()
    }


    override fun change512(str: String, strTo: String): String {
        return str.substringBeforeLast('/') + strTo
    }
}
