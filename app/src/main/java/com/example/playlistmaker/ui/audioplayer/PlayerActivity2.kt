package com.example.playlistmaker.ui.audioplayer

import android.content.Context
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.TypedValue
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import java.text.SimpleDateFormat
import java.util.Locale

class PlayerActivity2 : AppCompatActivity() {
    private lateinit var backButton: ImageButton
    private lateinit var nameSouth: TextView
    private lateinit var albumPicture: ImageView
    private lateinit var nameSinger: TextView
    private lateinit var longTime: TextView
    private lateinit var album: TextView
    private lateinit var nameAlbum: TextView
    private lateinit var playButton: ImageView
    private lateinit var previewUrl: String
    private lateinit var currTime: TextView

    private var mediaPlayer = MediaPlayer()
    private var playerState = STATE_DEFAULT

    private var currentTime: Int? = null

    private val dateFormat by lazy {
        SimpleDateFormat("mm : ss", Locale.getDefault())
    }

    private var handler: Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        var trackName = intent.getStringExtra("trackName")
        var pictureUrl = intent.getStringExtra("trackPicture")
        var singerName = intent.getStringExtra("nameSinger")
        var longTimeT = intent.getLongExtra("longTime", 0L)
        var albumT = intent.getStringExtra("album")
        previewUrl = intent.getStringExtra("url").toString()
        //Toast.makeText(this,"URL: ${previewUrl}",Toast.LENGTH_LONG).show()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player2)

        handler = Handler(Looper.getMainLooper())

        backButton = findViewById(R.id.backPlayer)
        backButton.setOnClickListener {
            finish()
        }

        val pictureUrl_512 = change512(pictureUrl.toString(), "/512x512bb.jpg")
        albumPicture = findViewById(R.id.albumPicture)
        val tmp: Int = dpToPx(8f, this)
        Glide
            .with(this)
            .load(pictureUrl_512)
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
            if(nameAlbum.visibility === View.GONE){
                album.visibility = View.VISIBLE
                nameAlbum.visibility = View.VISIBLE
            }
        }else{
            album.visibility = View.GONE
            nameAlbum.visibility = View.GONE
        }

        playButton = findViewById(R.id.PlayButton)
        preparePlayer()
        playButton.setOnClickListener {
            playbackControl()
        }
        currTime = findViewById(R.id.currentTimePlay)
    }

    private fun preparePlayer(){
        mediaPlayer.setDataSource(previewUrl)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener{
            playerState = STATE_PREPARED
            currentTime = 0
            currTime.text = dateFormat.format(currentTime)
        }
        mediaPlayer.setOnCompletionListener {
            playButton.setImageResource(R.drawable.playbutton)
            playerState = STATE_PREPARED
            currentTime = 0
            currTime.text = dateFormat.format(currentTime)
        }
    }
    override fun onPause() {
        super.onPause()
        pausePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }

    private fun playbackControl() {
        when(playerState) {
            STATE_PLAYING -> {
                pausePlayer()
            }
            STATE_PREPARED, STATE_PAUSED -> {
                startPlayer()
            }
        }
    }
    private fun timeSet(){
        handler?.post (
            createUpdateTime()
                )
    }

    private fun createUpdateTime(): Runnable {
        return object : Runnable{
            override fun run() {
                val currentLocalTime = mediaPlayer.currentPosition
                if (playerState == STATE_PLAYING && currentLocalTime >= 0){
                    val seconds = currentLocalTime!!
                    currTime.text = dateFormat.format(seconds)
                    handler?.postDelayed(this, DELAY)
                }else{
                    handler?.removeCallbacks(this)
                }
            }
        }
    }

    private fun startPlayer(){
        mediaPlayer.start()
        playButton.setImageResource(R.drawable.pause)
        playerState = STATE_PLAYING
        timeSet()
    }
    private fun pausePlayer(){

        mediaPlayer.pause()
        playButton.setImageResource(R.drawable.playbutton)
        playerState = STATE_PAUSED
    }

    private fun dpToPx(dp: Float, context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics
        ).toInt()
    }


    private fun change512(str: String, strTo: String): String {
        return str.substringBeforeLast('/') + strTo
    }

    companion object {
        private const val STATE_DEFAULT = 0
        private const val STATE_PREPARED = 1
        private const val STATE_PLAYING = 2
        private const val STATE_PAUSED = 3
        private const val DELAY = 1000L
    }
}
