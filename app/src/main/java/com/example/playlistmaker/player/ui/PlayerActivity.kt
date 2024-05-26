package com.example.playlistmaker.player.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.data.dto.DataService
import com.example.playlistmaker.data.dto.ServiceMethod
import com.example.playlistmaker.domain.api.MediaPlayerInterface
import com.example.playlistmaker.domain.impl.MediaPlayerImpl
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

    private val dateFormat by lazy {
        SimpleDateFormat("mm : ss", Locale.getDefault())
    }

    private var handler: Handler? = null

    private var player: MediaPlayerInterface = MediaPlayerImpl()
    private val service: ServiceMethod = DataService()

    override fun onCreate(savedInstanceState: Bundle?) {
        var trackName = intent.getStringExtra("trackName")
        var pictureUrl = intent.getStringExtra("trackPicture")
        var singerName = intent.getStringExtra("nameSinger")
        var longTimeT = intent.getLongExtra("longTime", 0L)
        var albumT = intent.getStringExtra("album")
        var countryName = intent.getStringExtra("country")
        previewUrl = intent.getStringExtra("url").toString()
        //Toast.makeText(this,"URL: ${previewUrl}",Toast.LENGTH_LONG).show()

        player.init(previewUrl)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player2)

        handler = Handler(Looper.getMainLooper())

        backButton = findViewById(R.id.backPlayer)
        backButton.setOnClickListener {
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

        if(player is MediaPlayerImpl) {
            player.preparePlayer()
            playButton.setOnClickListener {
                player.playbackControl()
                if (player.getStatus() == STATE_PLAYING) {
                    playButton.setImageResource(R.drawable.pause)
                    timeSet()
                } else {
                    playButton.setImageResource(R.drawable.playbutton)
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
                val currentLocalTime = player.getPlayer().currentPosition
                if (player.getStatus() == STATE_PLAYING && currentLocalTime >= 0) {
                    val seconds = currentLocalTime!!
                    currTime.text = dateFormat.format(seconds)
                    handler?.postDelayed(this, DELAY)
                }else if(player.getStatus()== STATE_PREPARED){
                    playButton.setImageResource(R.drawable.playbutton)
                    val seconds = 0
                    currTime.text = dateFormat.format(seconds)
                }
                else {
                    handler?.removeCallbacks(this)
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

//}
