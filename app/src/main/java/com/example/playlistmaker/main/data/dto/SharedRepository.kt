package com.example.playlistmaker.main.data.dto

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.SharedPreferences

interface SharedRepository {
    fun getSharedPreferences(activity: Application, identity:String):SharedPreferences
}
