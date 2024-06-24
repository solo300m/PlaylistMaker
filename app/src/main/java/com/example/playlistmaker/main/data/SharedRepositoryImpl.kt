package com.example.playlistmaker.main.data

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.example.playlistmaker.main.domain.models.SharedData
import com.example.playlistmaker.main.domain.SharedRepository

class SharedRepositoryImpl : SharedRepository {
    override fun getSharedPreferences(con: Context, identity: String): SharedData {
        return SharedData(con.getSharedPreferences(identity, MODE_PRIVATE), identity)
    }
}
