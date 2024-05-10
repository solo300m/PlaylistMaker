package com.example.playlistmaker.find
// класс для обработки операций по сохранению в sharedPreferences списков tracks и trackList
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.find.FindActivity.Companion.trackList
import com.example.playlistmaker.find.FindActivity.Companion.tracks
import java.util.ArrayList

const val TRACK_LIST_KEY: String = "list of 10 sings"
const val LIMIT_SAVE_LIST: Int = 10

class TrackPreferences { // класс для обработки операций по сохранению в sharedPreferences списков tracks и trackList
    val trackTmp =
        ArrayList<Track>() //временная переменная для сохранения Track на которой был осуществлен клик

    fun addTrackToList(unit: Track) { // формирование ограниченного списка сохраненных треков
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

    fun onFindToTrack(input: Long) { // поиск трека по клику на позиции в RecyclerView
        if (trackTmp.isNotEmpty()) {
            trackTmp.clear()
        }
        if (input != 0L && tracks.isNotEmpty()) {

            val findTrack = tracks.find { it.trackId == input }
            if (findTrack?.trackId == input)
                trackTmp.add(findTrack)
        }
    }
    fun getTrack(input: Long): Track? = tracks.find { it.trackId == input }

}
