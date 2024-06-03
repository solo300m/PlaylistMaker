package com.example.playlistmaker.main.domain.api

import android.content.SharedPreferences

interface AppStateInterface {
    fun readState(): Int
    fun writeState(state:Int)
    fun setNightMode()
    fun setLightMode()
}
