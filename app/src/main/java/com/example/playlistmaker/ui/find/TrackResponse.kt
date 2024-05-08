package com.example.playlistmaker.ui.find

import com.example.playlistmaker.domain.models.Track

class TrackResponse (
    val resultCount: String,
    val results: List<Track>
)
