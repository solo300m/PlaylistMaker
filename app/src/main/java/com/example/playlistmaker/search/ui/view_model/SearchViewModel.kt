package com.example.playlistmaker.search.ui.view_model

import android.app.Activity
import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.playlistmaker.player.domain.models.Track
import com.example.playlistmaker.search.data.dto.SaveListRepository
import com.example.playlistmaker.search.data.network.SharedPreferencesRepositoryImpl
import com.example.playlistmaker.search.data.network.SharedPreferencesRepository
import com.example.playlistmaker.search.data.dto.TRACK_LIST_KEY
import com.example.playlistmaker.search.data.dto.SaveListRepositoryImpl
import com.example.playlistmaker.search.data.network.NetworkClient
import com.example.playlistmaker.search.data.network.RetrofitNetworkClient

class SearchViewModel(application: Application) : AndroidViewModel(application) {
    val retrofit: NetworkClient = RetrofitNetworkClient()
    private val saveClient: SaveListRepository = SaveListRepositoryImpl()
    //private val sharedPref: SharedPreferencesRepository = SharedPreferencesRepositoryImpl(this[APPLICATION_KEY] as Activity)
    private val sharedPrefParam =
        application.getSharedPreferences(TRACK_LIST_KEY, MODE_PRIVATE)
    companion object {
        fun getViewModelFactory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                SearchViewModel(this[APPLICATION_KEY] as Application)
            }
        }
    }
    //Функции интерфейса SharedPreferencesInterface
    /*fun clearTrackList(){
        sharedPrefParam.clearTrackList()
    }
    fun loadList(){
        sharedPrefParam.loadList()
    }
    fun writeList(){
        sharedPrefParam.writeList()
    }

    //Функции интерфейса SaveListTrack реализованные в конкретном классе реализации
    fun getTrackTmp():ArrayList<Track>{
        return saveClient.getTracksTmp()
    }
    fun addTrackToList(unit: Track){
        saveClient.addTrackToList(unit)
    }
    fun onFindToTrack(input: Long){
        saveClient.onFindToTrack(input)
    }
    fun getTrack(input: Long): Track?{
        return saveClient.getTrack(input)
    }

    fun toastDiagnostic(msg:String){
        Toast.makeText(this.getApplication(),"Text",Toast.LENGTH_LONG).show()
    }*/
}
