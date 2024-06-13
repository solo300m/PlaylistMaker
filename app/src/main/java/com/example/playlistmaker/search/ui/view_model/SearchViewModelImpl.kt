package com.example.playlistmaker.search.ui.view_model

import android.app.Activity
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
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
) : AndroidViewModel(application) {
    companion object {
        fun getViewModelFactory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                SearchViewModelImpl(
                    this[APPLICATION_KEY] as Application, searchInteractor = SearchInteractorImpl(
                        saveListRepository = SaveListRepositoryImpl(),
                        networkClient = RetrofitNetworkClient(),
                        sharedPreferencesRepository = SharedPreferencesRepositoryImpl(this[APPLICATION_KEY] as Application)
                    )
                )
            }
        }
    }

    val tracks = MutableLiveData<MutableList<Track>>()

    init {
        tracks.value = mutableListOf()
    }

    val trackList = MutableLiveData<MutableList<Track>>()

    init {
        trackList.value = mutableListOf()
    }

    //Функции интерфейса SharedPreferencesInterface
    fun getTracksTmp(): ArrayList<Track> {
        return searchInteractor.getTracksTmp()
    }

    fun addTrackToList(unit: Track) {
        trackList.value?.let { searchInteractor.addTrackToList(it, unit) }
    }

    fun onFindToTrack(input: Long) {
        tracks.value?.let { searchInteractor.onFindToTrack(it, input) }
    }

    fun getTrack(input: Long): Track? {
        return tracks.value?.let { searchInteractor.getTrack(it, input) }
    }

    fun doRequest(dto: String): Response {
        return searchInteractor.doRequest(dto)
    }

    fun getITunesClient(): ITunes {
        return searchInteractor.getITunesClient()
    }

    fun clearTrackList() {
        trackList.value?.let {
            tracks.value?.let { it1 ->
                searchInteractor.clearTrackList(
                    it,
                    it1
                )
            }
        }
    }

    fun loadList() {
        trackList.value?.let { searchInteractor.loadList(it) }
    }

    fun writeList() {
        trackList.value?.let { searchInteractor.writeList(it) }
    }
}
