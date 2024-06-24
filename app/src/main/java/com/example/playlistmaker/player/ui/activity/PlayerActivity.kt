package com.example.playlistmaker.player.ui.activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.player.domain.models.IntentData
import com.example.playlistmaker.player.domain.models.PlayerData
import com.example.playlistmaker.player.ui.utils.DataService
import com.example.playlistmaker.player.ui.utils.ServiceMethod
import com.example.playlistmaker.player.ui.view_model.PlayerViewModel
import java.text.SimpleDateFormat
import java.util.Locale

class PlayerActivity : AppCompatActivity() {
    private lateinit var backButton: ImageButton
    private lateinit var nameSouth: TextView
    private lateinit var albumPicture: ImageView
    private lateinit var nameSinger: TextView
    private lateinit var longTime: TextView
    private lateinit var album: TextView
    private lateinit var country: TextView
    private lateinit var nameAlbum: TextView
    private lateinit var playButton: ImageView
    private lateinit var previewUrl: String
    private lateinit var currTime: TextView
    private lateinit var viewModelPlayer: PlayerViewModel
    private  lateinit var locIntent: IntentData

    private val dateFormat by lazy {
        SimpleDateFormat("mm : ss", Locale.getDefault())
    }

    private var handler: Handler? = null

    private val service: ServiceMethod = DataService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player2)
        locIntent = IntentData(intent = intent, intentStatus = true)

        viewModelPlayer = ViewModelProvider(
            this,
            PlayerViewModel.getViewModelFactory(locIntent)
        )[PlayerViewModel::class.java]
        //viewModelPlayer.testViewModelPlayer()
        val currentTrack = viewModelPlayer.getCurrentTrack()
        var stateLiveData = viewModelPlayer.getTrackPlayLiveData()

        viewModelPlayer.getTrackPlayLiveData().observe(this){
            stateLiveData = viewModelPlayer.getTrackPlayLiveData()
        }

        /*val trackId = currentTrack?.trackId
        val trackName = currentTrack?.trackName
        val pictureUrl = currentTrack?.artworkUrl100
        val singerName = currentTrack?.artistName
        val longTimeT = currentTrack?.trackTimeMillis
        val albumT = currentTrack?.collectionName
        val countryName = currentTrack?.country
        val realiseDate = currentTrack?.releaseDate
        val genreName = currentTrack?.primaryGenreName
        val previewUrl = currentTrack?.previewUrl*/

        val trackId = stateLiveData.value?.track?.trackId
        val trackName = stateLiveData.value?.track?.trackName
        val pictureUrl = stateLiveData.value?.track?.artworkUrl100
        val singerName = stateLiveData.value?.track?.artistName
        val longTimeT = stateLiveData.value?.track?.trackTimeMillis
        val albumT = stateLiveData.value?.track?.collectionName
        val countryName = stateLiveData.value?.track?.country
        val realiseDate = stateLiveData.value?.track?.releaseDate
        val genreName = stateLiveData.value?.track?.primaryGenreName
        val previewUrl = stateLiveData.value?.track?.previewUrl

        viewModelPlayer.init()
        //player.init(currentTrack.previewUrl)

        handler = Handler(Looper.getMainLooper())

        backButton = findViewById(R.id.backPlayer)
        backButton.setOnClickListener {
            viewModelPlayer.stopPlayer()
            finish()
        }

        val pictureUrl512 = service.setSizePicture512(pictureUrl.toString(), "/512x512bb.jpg")
        albumPicture = findViewById(R.id.albumPicture)
        val tmp: Int = service.dpToPx(8f, this)
        Glide
            .with(this)
            .load(pictureUrl512)
            .placeholder(R.drawable.placeholder)
            .transform(RoundedCorners(tmp))
            .into(albumPicture)


        nameSouth = findViewById(R.id.nameSouth)
        nameSouth.text = trackName

        nameSinger = findViewById(R.id.nameSinger)
        nameSinger.text = singerName

        longTime = findViewById(R.id.longTime)
        longTime.text = dateFormat.format(longTimeT)

        album = findViewById(R.id.album)
        nameAlbum = findViewById(R.id.nameAlbum)
        if (!albumT.isNullOrEmpty()) {
            album.text = albumT
            if (nameAlbum.visibility == View.GONE) {
                album.visibility = View.VISIBLE
                nameAlbum.visibility = View.VISIBLE
            }
        } else {
            album.visibility = View.GONE
            nameAlbum.visibility = View.GONE
        }
        playButton = findViewById(R.id.PlayButton)

        if (viewModelPlayer.getPlayer() is PlayerData) {
            viewModelPlayer.preparePlayer()
            playButton.setOnClickListener {
                viewModelPlayer.playbackControl()
                if (viewModelPlayer.getStatus() == STATE_PLAYING) {
                    playButton.setImageResource(R.drawable.pause)
                    timeSet()
                } else {
                    playButton.setImageResource(R.drawable.playbutton)
                    //Toast.makeText(this, "Status ${viewModelPlayer.getStatus()}",Toast.LENGTH_LONG).show()
                }
            }

        }
        currTime = findViewById(R.id.currentTimePlay)
        country = findViewById(R.id.country)
        country.text = countryName
    }

    private fun timeSet() {
        handler?.post(
            createUpdateTime()
        )
    }

    private fun createUpdateTime(): Runnable {
        return object : Runnable {
            override fun run() {
                val currentLocalTime = viewModelPlayer.getPlayer()?.mediaPlayer?.currentPosition
                if (currentLocalTime != null) {
                    if (viewModelPlayer.getStatus() == STATE_PLAYING && currentLocalTime >= 0) {
                        val seconds = currentLocalTime!!
                        currTime.text = dateFormat.format(seconds)
                        handler?.postDelayed(this, DELAY)
                    } else if (viewModelPlayer.getStatus() == STATE_PREPARED) {
                        playButton.setImageResource(R.drawable.playbutton)
                        val seconds = 0
                        currTime.text = dateFormat.format(seconds)
                    } else {
                        handler?.removeCallbacks(this)
                    }
                }
            }
        }
    }

    companion object {
        private const val STATE_DEFAULT = 0
        private const val STATE_PREPARED = 1
        private const val STATE_PLAYING = 2
        private const val STATE_PAUSED = 3
        private const val DELAY = 1000L
    }
}
