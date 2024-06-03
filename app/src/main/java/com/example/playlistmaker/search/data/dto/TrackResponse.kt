package com.example.playlistmaker.search.data.dto

import com.example.playlistmaker.player.domain.models.Track
import com.example.playlistmaker.search.data.dto.Response

class TrackResponse(
    val results: List<Track>
) : Response()
