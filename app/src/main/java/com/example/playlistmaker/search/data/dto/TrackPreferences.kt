package com.example.playlistmaker.search.data.dto
// класс для обработки операций по сохранению в sharedPreferences списков tracks и trackList
import com.example.playlistmaker.player.domain.models.Track
import com.example.playlistmaker.search.ui.activity.FindActivity.Companion.trackList
import com.example.playlistmaker.search.ui.activity.FindActivity.Companion.tracks
import kotlin.collections.ArrayList

const val TRACK_LIST_KEY: String = "list of 10 sings"
const val LIMIT_SAVE_LIST: Int = 10

class TrackPreferences : SaveListTrack{ // класс для обработки операций по сохранению в sharedPreferences списков tracks и trackList
    private val trackTmp =
        ArrayList<Track>() //временная переменная для сохранения Track на которой был осуществлен клик

    override fun getTracksTmp(): ArrayList<Track> {
        return trackTmp
    }

    override fun addTrackToList(unit: Track) { // формирование ограниченного списка сохраненных треков
        var idx = -1
        for (i in 0..trackList.size - 1) {
            if (trackList[i] == unit) {
                idx = i
                break
            }
        }
        if (idx != -1) {
            trackList.removeAt(idx)
        }
        if (trackList.size < LIMIT_SAVE_LIST) {
            trackList.add(unit)
        } else {
            trackList.removeAt(0)
            trackList.add(unit)
        }
    }

    override fun onFindToTrack(input: Long) { // поиск трека по клику на позиции в RecyclerView
        if (trackTmp.isNotEmpty()) {
            trackTmp.clear()
        }
        if (input != 0L && tracks.isNotEmpty()) {

            val findTrack = tracks.find { it.trackId == input }
            if (findTrack?.trackId == input)
                trackTmp.add(findTrack)
        }
    }
    override fun getTrack(input: Long): Track? = tracks.find { it.trackId == input }

}