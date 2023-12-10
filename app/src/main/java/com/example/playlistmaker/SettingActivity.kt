package com.example.playlistmaker

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val homeButton = findViewById<ImageView>(R.id.backSettingToMain)
        homeButton.setOnClickListener {
            val intent2 = Intent(this, MainActivity::class.java)
            startActivity(intent2)
        }
        val sareButton = findViewById<ImageView>(R.id.sareButton);
        sareButton.setOnClickListener {
            val message = "Курс по Андроид разработке на Яндекс.Практикум. https://practicum.yandex.ru/android-developer/?utm_source=yandex&utm_medium=cpc&utm_campaign=Yan_Net_RF_Andd_460&utm_content=sty_context%3As_mail.yandex.ru%3Acid_75977624%3Agid_5009705635%3Akw_skillbox+android+%D1%80%D0%B0%D0%B7%D1%80%D0%B0%D0%B1%D0%BE%D1%82%D1%87%D0%B8%D0%BA%3Apid_40761299115%3Aaid_13214318978%3Acrid_0%3Arid_40761299115%3Ap_0%3Apty_none%3Amty_%3Amkw_%3Adty_desktop%3Acgcid_0%3Arn_%D0%98%D0%B6%D0%B5%D0%B2%D1%81%D0%BA%3Arid_44&utm_term=skillbox+android+%D1%80%D0%B0%D0%B7%D1%80%D0%B0%D0%B1%D0%BE%D1%82%D1%87%D0%B8%D0%BA&yclid=4129482520796652835"
            val sareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT,message)
                type="text/plain"
            }
            startActivity(sareIntent)
        }
        val supButton = findViewById<ImageView>(R.id.supportButton)
        supButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type="text/plain"
                putExtra(Intent.EXTRA_EMAIL, arrayOf("solo300m@yandex.ru"))
                putExtra(Intent.EXTRA_SUBJECT, "Сообщение разработчикам и разработчицам приложения Playlist Maker")
                putExtra(Intent.EXTRA_TEXT, "Спасибо разработчикам и разработчицам за крутое приложение!")
            }
            startActivity(intent)
        }
        val termsButton = findViewById<ImageView>(R.id.termsButton)
        termsButton.setOnClickListener {
            val intent:Intent = Uri.parse("https://yandex.ru/legal/practicum_offer/").let{
                webpage -> Intent(Intent.ACTION_VIEW, webpage)
            }
           startActivity(intent)
        }
    }

}
