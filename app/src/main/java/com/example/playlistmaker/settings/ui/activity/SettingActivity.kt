package com.example.playlistmaker.settings.ui.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.playlistmaker.sharing.ui.activity.MediatekaActivity
import com.example.playlistmaker.R
import com.example.playlistmaker.search.ui.activity.FindActivity
import com.example.playlistmaker.settings.ui.view_model.SettingViewModel

const val NIGHT_CONST: Int = 2

class SettingActivity : AppCompatActivity() {
    private lateinit var viewModelSetting: SettingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        viewModelSetting = ViewModelProvider(
            this,
            SettingViewModel.getViewModelFactory()
        )[SettingViewModel::class.java]
        //viewModelSetting.testViewModel("Setting viewModel started!")

        //val sharedSet = getSharedPreferences(SETTING_SAVE, MODE_PRIVATE)
        //val sharedSet = viewModelSetting.getSharedPreferences()
        //val stateSave: AppStateInterface = AppState(sharedSet)
        val cont = findViewById<ImageView>(R.id.control)
        //var stateG = stateSave.readState()

        if (viewModelSetting.readState() == NIGHT_CONST) {
            viewModelSetting.setNightMode()

            cont.setImageResource(R.drawable.control2)
        } else {
            viewModelSetting.setLightMode()

            cont.setImageResource(R.drawable.control)
        }

        cont.setOnClickListener {

            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO) {
                cont.setImageResource(R.drawable.control2)
                viewModelSetting.setNightMode()

                viewModelSetting.writeState(AppCompatDelegate.getDefaultNightMode())
            } else {
                cont.setImageResource(R.drawable.control)
                viewModelSetting.setLightMode()

                viewModelSetting.writeState(AppCompatDelegate.getDefaultNightMode())
            }
        }

        val findBut = findViewById<Button>(R.id.findComeButton)
        findBut.setOnClickListener {
            startActivity(Intent(this, FindActivity::class.java))
        }

        val mediaBut = findViewById<Button>(R.id.mediaComeButton)
        mediaBut.setOnClickListener {
            startActivity(Intent(this, MediatekaActivity::class.java))
        }

        val setting = findViewById<Button>(R.id.setComeButton)
        setting.setTextColor(resources.getColor(R.color.blue_yp, null))


        val homeButton = findViewById<ImageView>(R.id.backSettingToMain)
        homeButton.setOnClickListener {
            finish()
        }
        val sareButton = findViewById<ImageView>(R.id.sareButton);
        sareButton.setOnClickListener {
            val message = resources.getString(R.string.promoText)
            val sareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, message)
                type = "text/plain"
            }
            startActivity(sareIntent)
        }
        val supButton = findViewById<ImageView>(R.id.supportButton)
        supButton.setOnClickListener {

            val title: String = resources.getString(R.string.titleEmailTest)
            val substr: String = resources.getString(R.string.subjectTest)
            val intSend = Intent(Intent.ACTION_SENDTO)
            intSend.data = Uri.parse("mailto:")
            intSend.putExtra(Intent.EXTRA_EMAIL, arrayOf(resources.getString(R.string.testMail)))
            intSend.putExtra(Intent.EXTRA_SUBJECT, title)
            intSend.putExtra(Intent.EXTRA_TEXT, substr)

            startActivity(intSend)

        }
        val termsButton = findViewById<ImageView>(R.id.termsButton)
        termsButton.setOnClickListener {
            val intentContent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(resources.getString(R.string.yandexText))
            )
            startActivity(intentContent)
        }
    }


}
