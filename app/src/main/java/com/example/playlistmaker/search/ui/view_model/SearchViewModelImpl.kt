package com.example.playlistmaker.search.ui.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.playlistmaker.creator.Creator
import com.example.playlistmaker.player.domain.models.Track
import com.example.playlistmaker.search.domain.model.TracksListModel
import com.example.playlistmaker.player.domain.models.TracksModel
import com.example.playlistmaker.search.domain.model.ITunes
import com.example.playlistmaker.search.domain.model.Response
import com.example.playlistmaker.search.domain.SearchInteractor

class SearchViewModelImpl(
    application: Application,
    private val searchInteractor: SearchInteractor,
) : AndroidViewModel(application) {
    companion object {
        fun getViewModelFactory(): ViewModelProvider.Factory = viewModelFactory {
            /*initializer {
                SearchViewModelImpl(
                    this[APPLICATION_KEY] as Application, searchInteractor = SearchInteractorImpl(
                        saveListRepository = SaveListRepositoryImpl(),
                        networkClient = RetrofitNetworkClient(),
                        sharedPreferencesRepository = SharedPreferencesRepositoryImpl(this[APPLICATION_KEY] as Application)
                    )
                )
            }*/
            initializer {
                SearchViewModelImpl(
                    this[APPLICATION_KEY] as Application,
                    Creator.getSearchInteractor(this[APPLICATION_KEY] as Application)
                )
            }
        }
    }

    val tracks = MutableLiveData<TracksModel>()
    init {
            val tmpTrackModel = TracksModel()
            //tracks.value?.tracks = mutableListOf()
            tracks.value = tmpTrackModel
            //tracks.value?.statusTracks = false
    }

    fun getTracksLiveData():LiveData<TracksModel> = tracks
    val trackList = MutableLiveData<TracksListModel>()
    init {
            val tmpTracksListModel = TracksListModel()
            //trackList.value?.trackList = mutableListOf()
            trackList.value = tmpTracksListModel
            //trackList.value?.statusTracksList = false
    }
    fun getTrackListLiveData():LiveData<TracksListModel> = trackList
    //Функции интерфейса SharedPreferencesInterface
    fun getTracksTmp(): ArrayList<Track> {
        return searchInteractor.getTracksTmp()
    }

    fun addTrackToList(unit: Track) {
        trackList.value?.trackList?.let { searchInteractor.addTrackToList(it, unit) }
        if(trackList.value?.statusTracksList == false)
            trackList.value?.statusTracksList = true
    }

    fun onFindToTrack(input: Long) {
        tracks.value?.tracks?.let { searchInteractor.onFindToTrack(it, input) }
    }

    fun getTrack(input: Long): Track? {
        return tracks.value?.tracks?.let { searchInteractor.getTrack(it, input) }
    }

    fun doRequest(dto: String): Response {
        return searchInteractor.doRequest(dto)
    }

    fun getITunesClient(): ITunes {
        return searchInteractor.getITunesClient()
    }

    fun clearTrackList() {
        trackList.value?.trackList?.let {
            tracks.value?.tracks?.let { it1 ->
                searchInteractor.clearTrackList(
                    it,
                    it1
                )
            }
        }
        tracks.value?.statusTracks = false
        trackList.value?.statusTracksList = false
    }

    fun loadList() {
        trackList.value?.trackList?.let { searchInteractor.loadList(it) }
        trackList.value?.statusTracksList = true
    }

    fun writeList() {
        trackList.value?.trackList?.let { searchInteractor.writeList(it) }
        //trackList.value?.statusTracksList = false
    }
}
