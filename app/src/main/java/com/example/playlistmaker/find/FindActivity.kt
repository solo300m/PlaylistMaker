package com.example.playlistmaker.find

import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View

import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.R
import com.google.gson.Gson

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FindActivity : AppCompatActivity() {
    private lateinit var editText: EditText
    private lateinit var clearBtn: ImageView
    private lateinit var recyclerView: RecyclerView
    //private lateinit var recyclerBlock: LinearLayout
    private lateinit var findButton: ImageView
    private lateinit var place_200: LinearLayout
    private lateinit var place_500: LinearLayout
    private lateinit var updateButton: Button
    private lateinit var titleFind: TextView//заголовок сохраненного списка
    private lateinit var clearButtonFind: Button//кнопка очистки списка сохраненных треков


    private val adapter = TrackAdapter(tracks)

    private var input: String? = null


    private val retrofit = Retrofit.Builder()
        .baseUrl(tracBaseURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val trackService = retrofit.create(IMDbApi::class.java)
    private val findVM:FindViewModel by lazy{
        ViewModelProvider(this).get(FindViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find)

        titleFind = findViewById(R.id.titleFind)
        clearButtonFind = findViewById(R.id.clearFind)
        val sharedPref = getSharedPreferences(TRACK_LIST_KEY, MODE_PRIVATE)//инициация SharedPreferences
        loadList(sharedPref) // загрузка сохраненного списка из sharedPreferences
        if (trackList.isNotEmpty()) {
            tracks.clear()
            tracks.addAll(trackList.reversed())// загрузка сохраненного списка из sharedPreferences в реверсивном виде
            titleFind.visibility = View.VISIBLE // включение видимости заголовка "Вы искали"
            clearButtonFind.visibility = View.VISIBLE //кнопка "Очистить список" видимость true
        }else{
            titleFind.visibility = View.GONE // Выключение заголовка
            clearButtonFind.visibility = View.GONE //Выключение списка
        }

        place_200 = findViewById(R.id.placeholder_200)
        place_500 = findViewById(R.id.placeholder_500)
        updateButton = findViewById(R.id.updateButton)
        findButton = findViewById(R.id.backFindToMain)



        findButton.setOnClickListener {
            finish()
        }

        clearButtonFind.setOnClickListener {//обработчик кнопки "Очистка списка"
            clearTrackList(sharedPref)
            titleFind.visibility = View.GONE
            clearButtonFind.visibility = View.GONE
            recyclerView.adapter?.notifyDataSetChanged()
        }

        editText = findViewById<EditText>(R.id.searchField)
        // val linLayout = findViewById<LinearLayout>(R.id.editContainer)


        clearBtn = findViewById(R.id.clearIcon)

        clearBtn.setOnClickListener {
            titleFind.visibility = View.VISIBLE
            clearButtonFind.visibility = View.VISIBLE
            editText.setText("")
            tracks.clear()
            writeList(sharedPref) //сохранение списка в sharedPreferences
            loadList(sharedPref) //загрузка из sharedPreferences
            if (trackList.isNotEmpty()) {
                tracks.addAll(trackList.reversed()) //reversed для обеспечения первой позиции последней запрошенной записи
            }
            recyclerView.adapter?.notifyDataSetChanged()
            place_200.visibility = View.GONE

            val view = this.currentFocus
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            inputMethodManager?.hideSoftInputFromWindow(view?.windowToken, 0)
        }
        if (savedInstanceState != null) {
            editText.setText(savedInstanceState.getString(TEXT_VIEW_KEY))
        }
        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty()) {
                    input = s.toString()
                }
                clearBtn.visibility = clearButtonVisibility(s)
            }

            override fun afterTextChanged(s: Editable?) {

            }
        }
        editText.addTextChangedListener(simpleTextWatcher)
        editText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                titleFind.visibility = View.GONE //выключение заголовка "Вы искали"
                clearButtonFind.visibility = View.GONE //Выключение кнопки "Очистка списка"


                onFindToInternet()
                true
            }
            false
        }
        recyclerView = findViewById(R.id.findList)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter

        updateButton.setOnClickListener {
            onFindToInternet()
        }

    }

    private fun clearTrackList(sharedPreferences: SharedPreferences) {
        trackList.clear()
        tracks.clear()
        writeList(sharedPreferences)
    }

    fun loadList(sharedPreferences: SharedPreferences) { // функция выгрузки из sharedPreferences в переменную trackList
        val tmpArray = read(sharedPreferences);
        trackList.clear()
        trackList.addAll(tmpArray)
    }

    fun writeList(sharedPreferences: SharedPreferences) { // функция сохранения в sharedPreferences из trackList
        write(sharedPreferences, trackList)
    }

    private fun read(sharedPreferences: SharedPreferences): Array<Track> { // функция выгрузки из sharedPreferences служебная
        val json = sharedPreferences.getString(TRACK_LIST_KEY, null) ?: return emptyArray()
        return Gson().fromJson(json, Array<Track>::class.java)
    }

    private fun write(sharedPreferences: SharedPreferences, tracksList: List<Track>) { // функция сохранения в sharedPreferences служебная
        val json = Gson().toJson(tracksList)
        sharedPreferences.edit()
            .putString(TRACK_LIST_KEY, json)
            .apply()
    }

    private fun onFindToInternet() {
        if (!input.isNullOrEmpty()) {
            trackService.search(input.toString()).enqueue(object : Callback<TrackResponse> {
                override fun onResponse(
                    call: Call<TrackResponse>,
                    response: Response<TrackResponse>
                ) {
                    if (response.code() == 200) {
                        tracks.clear()
                        if (response.body()?.results?.isNotEmpty() == true) {
                            if (place_500.visibility == View.VISIBLE) {
                                place_500.visibility = View.GONE
                            }
                            tracks.addAll(response.body()?.results!!)
                            recyclerView.adapter?.notifyDataSetChanged()

                        }
                        if (tracks.isEmpty()) {

                            place_200.visibility = View.VISIBLE
                        }
                    } else {
                        val code = response.code()
                        val tmp = "" + R.string.error_of_server + " ${code}"

                        place_500.visibility = View.VISIBLE
                    }
                }

                override fun onFailure(call: Call<TrackResponse>, t: Throwable) {

                    place_500.visibility = View.VISIBLE
                }
            })
        }
    }

    private fun clearButtonVisibility(s: CharSequence?): Int {
        if (s.isNullOrEmpty()) {
            return View.GONE
        } else {
            return View.VISIBLE
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val tmp = savedInstanceState.getString(TEXT_VIEW_KEY, " ")
        if (!tmp.isNullOrEmpty()) {
            editText.setText(tmp.toString())
        } else {
            editText.setText("")
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState?.run {
            putString(TEXT_VIEW_KEY, editText.text.toString())
        }
    }

    companion object {
        val tracks = ArrayList<Track>()
        var trackList: MutableList<Track> = mutableListOf()
        const val tracBaseURL = "https://itunes.apple.com"
        private const val TEXT_VIEW_KEY = "TEXT_VIEW_KEY"
    }
}
