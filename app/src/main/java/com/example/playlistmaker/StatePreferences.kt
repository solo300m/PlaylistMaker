package com.example.playlistmaker

import android.content.SharedPreferences
import com.google.gson.Gson

const val USER_STATE = "current state"

class StatePreferences {
    fun read(sharedPreferences: SharedPreferences):State?{
        val json = sharedPreferences.getString(USER_STATE, null)?: return null
        return Gson().fromJson(json, State::class.java)
    }
    fun write(sharedPreferences: SharedPreferences, state: State){
        val json = Gson().toJson(state)
        sharedPreferences.edit()
            .putString(USER_STATE, json)
            .apply()
    }
}
