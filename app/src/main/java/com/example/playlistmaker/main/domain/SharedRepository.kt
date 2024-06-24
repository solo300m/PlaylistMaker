package com.example.playlistmaker.main.domain

import android.content.Context
import com.example.playlistmaker.main.domain.models.SharedData

interface SharedRepository {
    fun getSharedPreferences(context: Context, identity:String): SharedData
}
