package com.example.playlistmaker.main.data.dto

import android.app.Activity
import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class SharedRepositoryImpl:SharedRepository {
    override fun getSharedPreferences(activity: Application, identity: String): SharedPreferences {
        val shared = activity.getSharedPreferences(identity, MODE_PRIVATE)
        return shared
    }
}
