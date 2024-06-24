package com.example.playlistmaker.main.domain.models

import android.content.SharedPreferences


data class SharedData(
    var sharedPreferences: SharedPreferences?,
    var exsseption: String?,
)
