package com.example.playlistmaker.search.ui.view_model

import android.app.Activity
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.playlistmaker.player.domain.models.Track
import com.example.playlistmaker.search.data.dto.ITunes
import com.example.playlistmaker.search.data.dto.Response
import com.example.playlistmaker.search.data.dto.SaveListRepositoryImpl
import com.example.playlistmaker.search.data.network.RetrofitNetworkClient
import com.example.playlistmaker.search.data.network.SharedPreferencesRepositoryImpl
import com.example.playlistmaker.search.domain.SearchInteractor
import com.example.playlistmaker.search.domain.SearchInteractorImpl

class SearchViewModelImpl(
    application: Application,
    private val searchInteractor: SearchInteractor,
):AndroidViewModel(application) {
    companion object {
        fun getViewModelFactory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                SearchViewModelImpl(this[APPLICATION_KEY] as Application, searchInteractor = SearchInteractorImpl(
                    saveListRepository = SaveListRepositoryImpl(),
                    networkClient = RetrofitNetworkClient(),
                    sharedPreferencesRepository = SharedPreferencesRepositoryImpl(this[APPLICATION_KEY] as Application)
                ))
            }
        }
    }

    //Функции интерфейса SharedPreferencesInterface
    fun getTracksTmp(): ArrayList<Track> {
        return searchInteractor.getTracksTmp()
    }

    fun addTrackToList(unit: Track) {
        searchInteractor.addTrackToList(unit)
    }

    fun onFindToTrack(input: Long) {
        searchInteractor.onFindToTrack(input)
    }

    fun getTrack(input: Long): Track? {
        return searchInteractor.getTrack(input)
    }

    fun doRequest(dto: String): Response {
        return searchInteractor.doRequest(dto)
    }

    fun getITunesClient(): ITunes {
        return searchInteractor.getITunesClient()
    }

    fun clearTrackList() {
        searchInteractor.clearTrackList()
    }

    fun loadList() {
        searchInteractor.loadList()
    }

    fun writeList() {
        searchInteractor.writeList()
    }
}
