package com.example.playlistmaker.search.domain

import com.example.playlistmaker.player.domain.models.Track
import com.example.playlistmaker.search.data.dto.ITunes
import com.example.playlistmaker.search.data.dto.Response
import com.example.playlistmaker.search.data.dto.SaveListRepository
import com.example.playlistmaker.search.data.network.NetworkClient
import com.example.playlistmaker.search.data.network.SharedPreferencesRepository

class SearchInteractorImpl(
    private val saveListRepository: SaveListRepository,
    private val networkClient: NetworkClient,
    private val sharedPreferencesRepository: SharedPreferencesRepository,
) : SearchInteractor {

    override fun getTracksTmp(): ArrayList<Track> {
        return saveListRepository.getTracksTmp()
    }

    override fun addTrackToList(unit: Track) {
        saveListRepository.addTrackToList(unit)
    }

    override fun onFindToTrack(input: Long) {
        saveListRepository.onFindToTrack(input)
    }

    override fun getTrack(input: Long): Track? {
        return saveListRepository.getTrack(input)
    }

    override fun doRequest(dto: String): Response {
        return networkClient.doRequest(dto)
    }

    override fun getITunesClient(): ITunes {
        return networkClient.getITunesClient()
    }

    override fun clearTrackList() {
        sharedPreferencesRepository.clearTrackList()
    }

    override fun loadList() {
        sharedPreferencesRepository.loadList()
    }

    override fun writeList() {
        sharedPreferencesRepository.writeList()
    }
}
