package com.example.playlistmaker.player

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.Display
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
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

    private val dateFormat by lazy {
        SimpleDateFormat("mm : ss", Locale.getDefault())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        var trackName = intent.getStringExtra("trackName")
        var pictureUrl = intent.getStringExtra("trackPicture")
        var singerName = intent.getStringExtra("nameSinger")
        var longTimeT = intent.getLongExtra("longTime", 0L)
        var albumT = intent.getStringExtra("album")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player2)

        backButton = findViewById(R.id.backPlayer)
        backButton.setOnClickListener {
            finish()
        }

        val pictureUrl_512 = change512(pictureUrl.toString(), "/512x512bb.jpg")
        //Toast.makeText(this, "${pictureUrl_512}", Toast.LENGTH_LONG).show()
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
    }

    private fun dpToPx(dp: Float, context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics
        ).toInt()
    }

    private fun pxToDp(px: Float, context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_PX,
            px,
            context.resources.displayMetrics
        ).toInt()
    }

    private fun displayHeight(cont: Context): Int {
        val dspl = cont.resources.displayMetrics
        val tmp = dspl.heightPixels
        return tmp
    }

    private fun displayWidth(cont: Context): Int {
        val dspl = cont.resources.displayMetrics
        val tmp = dspl.widthPixels
        return tmp
    }

    private fun change512(str: String, strTo: String): String {
        return str.substringBeforeLast('/') + strTo
    }
}
