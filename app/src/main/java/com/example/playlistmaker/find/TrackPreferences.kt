package com.example.playlistmaker.find


import com.example.playlistmaker.find.FindActivity.Companion.trackList
import com.example.playlistmaker.find.FindActivity.Companion.tracks
import java.util.ArrayList

const val TRACK_LIST_KEY: String = "list of 10 signs"

class TrackPreferences {
    //var trackList: MutableList<Track> = mutableListOf()
    val trackTmp = ArrayList<Track>()

    fun addTrackToList(unit: Track) {
        var idx = -1
        if (unit != null) {
            for (i in 0..trackList.size-1) {
                if (trackList[i] == unit) {
                    idx = i
                    break
                }
            }
            if(idx != -1){
                trackList.removeAt(idx)
            }
            if (trackList.size < 10) {
                trackList.add(unit)
            } else {
                trackList.removeAt(0)
                trackList.add(unit)
            }
        }
    }

    fun onFindToTrack(input: Long) {
        if (input != 0L && !tracks.isEmpty()) {
            for(i in 0..tracks.size-1){
                if(tracks[i].trackId == input){
                    trackTmp.add(tracks[i])
                }
            }
        }
    }

}
