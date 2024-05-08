package com.example.playlistmaker.ui.find

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel

class FindViewModel:ViewModel() {
    init {
        Log.d("MYAPPLOG", "ViewModel instance created")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("MYAPPLOG", "ViewModel destroyed")
    }
}
