package com.example.playlistmaker.search.domain

import com.example.playlistmaker.player.domain.models.Track
import com.example.playlistmaker.search.data.SaveListRepository
import com.example.playlistmaker.search.data.SharedPreferencesRepository
import com.example.playlistmaker.search.domain.model.ITunes
import com.example.playlistmaker.search.domain.model.Response
import com.example.playlistmaker.search.data.network.NetworkClient

class SearchInteractorImpl(
    private val saveListRepository: SaveListRepository,
    private val networkClient: NetworkClient,
    private val sharedPreferencesRepository: SharedPreferencesRepository,
) : SearchInteractor {

    override fun getTracksTmp(): ArrayList<Track> {
        return saveListRepository.getTracksTmp()
    }

    override fun addTrackToList(trackList:MutableList<Track>, unit: Track) {
        saveListRepository.addTrackToList(trackList, unit)
    }

    override fun onFindToTrack(tracks:MutableList<Track>, input: Long) {
        saveListRepository.onFindToTrack(tracks, input)
    }

    override fun getTrack(tracks:MutableList<Track>, input: Long): Track? {
        return saveListRepository.getTrack(tracks, input)
    }

    override fun doRequest(dto: String): Response {
        return networkClient.doRequest(dto)
    }

    override fun getITunesClient(): ITunes {
        return networkClient.getITunesClient()
    }

    override fun clearTrackList(trackList:MutableList<Track>, tracks:MutableList<Track>) {
        sharedPreferencesRepository.clearTrackList(trackList, tracks)
    }

    override fun loadList(trackList:MutableList<Track>) {
        sharedPreferencesRepository.loadList(trackList)
    }

    override fun writeList(trackList:MutableList<Track>) {
        sharedPreferencesRepository.writeList(trackList)
    }
}
