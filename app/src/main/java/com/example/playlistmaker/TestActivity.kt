package com.example.playlistmaker

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.find.Track

class TestActivity : AppCompatActivity() {
    private lateinit var backButton:ImageButton
    private lateinit var title:TextView
    private lateinit var picture:ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        var trackName = intent.getStringExtra("trackName")
        var pictureUrl = intent.getStringExtra("trackPicture")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        backButton = findViewById(R.id.backButton)
        title = findViewById(R.id.textTest)
        picture = findViewById(R.id.pictureTest)

        val tmp: Int = dpToPx(8f, this)
        Glide
            .with(this)
            .load(pictureUrl)
            .placeholder(R.drawable.placeholder)
            .transform(RoundedCorners(tmp))
            .into(picture)
        title.text = trackName

        backButton.setOnClickListener{
            finish()
        }
    }
    private fun dpToPx(dp: Float, context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics).toInt()
    }
}
