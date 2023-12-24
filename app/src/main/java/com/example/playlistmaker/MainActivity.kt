package com.example.playlistmaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.playlistmaker.find.FindActivity
import java.io.Serializable


data class Buffer(
    val text1:String,
    val text2:String
):Serializable
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //val image = findViewById<ImageView>(R.id.poster)
        //реализация ананимного класса
        /*val imageClickListener: View.OnClickListener = object : View.OnClickListener{
            override fun onClick(v: View?) {
                Toast.makeText(this@MainActivity, "Нажали на картинку", Toast.LENGTH_LONG).show()
            }
        }
        image.setOnClickListener(imageClickListener)*/

        //лямбда выражение
        /*image.setOnClickListener{
            Toast.makeText(this@MainActivity, "Нажали на картинку", Toast.LENGTH_SHORT).show()
        }*/
        val findButton = findViewById<Button>(R.id.find)
        val mTeka = findViewById<Button>(R.id.mediateka)
        val settButton = findViewById<Button>(R.id.setting)

        findButton.setOnClickListener {
            val textOut:Buffer = Buffer("Text first parameter", "Text second parameter")
            val message = "Hello, My friend - programmer!"
            val intent = Intent(this, FindActivity::class.java)
            intent.putExtra("textOut", textOut);
            startActivity(intent)
        };

        mTeka.setOnClickListener {
            //val intent = Intent(this, MediatekaActivity::class.java)
            //Toast.makeText(this@MainActivity, "Нажата кнопка Медиатека!", Toast.LENGTH_SHORT ).show()
            startActivity(Intent(this, MediatekaActivity::class.java))
        }
        settButton.setOnClickListener {
            //val intent = Intent(this, SettingActivity::class.java)
            //Toast.makeText(this@MainActivity, "Нажата кнопка Настройки", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, SettingActivity::class.java))
        }
    }
}
