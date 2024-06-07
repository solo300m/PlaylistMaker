package com.example.playlistmaker.main.domain.api

interface AppStateInteractor {
    fun readState(): Int
    fun writeState(state:Int)
    fun setNightMode()
    fun setLightMode()
}
