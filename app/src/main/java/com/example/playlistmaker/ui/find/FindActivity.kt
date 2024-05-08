package com.example.playlistmaker.ui.find

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View

import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.R
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.ui.audioplayer.PlayerActivity2
import com.example.playlistmaker.ui.audioplayer.PlayerActivity21
import com.google.gson.Gson

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FindActivity : AppCompatActivity(), TrackViewHolder.Listener {
    private lateinit var editText: EditText
    private lateinit var clearBtn: ImageView
    private lateinit var recyclerView: RecyclerView

    private lateinit var findButton: ImageView
    private lateinit var place_200: LinearLayout
    private lateinit var place_500: LinearLayout
    private lateinit var updateButton: Button
    private lateinit var titleFind: TextView//заголовок сохраненного списка
    private lateinit var clearButtonFind: Button//кнопка очистки списка сохраненных треков
    private lateinit var findList:RecyclerView //список - результат поиска
    private lateinit var progressBar:ProgressBar // виджет ProgressBar
    private var objectSave = TrackPreferences()

    private val adapter = TrackAdapter(tracks, this)

    private var input: String? = null


    private val retrofit = Retrofit.Builder()
        .baseUrl(tracBaseURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val trackService = retrofit.create(IMDbApi::class.java)
    private val findVM: FindViewModel by lazy {
        ViewModelProvider(this).get(FindViewModel::class.java)
    }
    private val searchRunnable = Runnable{onFindToInternet()} // Runnable объект для debonce поисковой строки
    private val handlerFind = Handler(Looper.getMainLooper()) // Handler главного IU потока
    companion object {
        val tracks = ArrayList<Track>()
        var trackList: MutableList<Track> = mutableListOf()
        const val tracBaseURL = "https://itunes.apple.com"
        private const val TEXT_VIEW_KEY = "TEXT_VIEW_KEY"
        var currentTrack: Track? = null;
        private const val SEARCH_DELAY = 2000L
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find)

        progressBar = findViewById(R.id.progressBar)

        titleFind = findViewById(R.id.titleFind)
        clearButtonFind = findViewById(R.id.clearFind)
        val sharedPref =
            getSharedPreferences(TRACK_LIST_KEY, MODE_PRIVATE)//инициация SharedPreferences
        loadList(sharedPref) // загрузка сохраненного списка из sharedPreferences
        if (trackList.isNotEmpty()) {
            tracks.clear()
            tracks.addAll(trackList.reversed())// загрузка сохраненного списка из sharedPreferences в реверсивном виде
            onTitleAndButton()
        } else {
            offTitleAndButton()
        }

        place_200 = findViewById(R.id.placeholder_200)
        place_500 = findViewById(R.id.placeholder_500)
        updateButton = findViewById(R.id.updateButton)
        findButton = findViewById(R.id.backFindToMain)
        findList = findViewById(R.id.findList)


        findButton.setOnClickListener {
            finish()
        }

        clearButtonFind.setOnClickListener {//обработчик кнопки "Очистка списка"
            clearTrackList(sharedPref)
            offTitleAndButton()
            recyclerView.adapter?.notifyDataSetChanged()
        }

        editText = findViewById<EditText>(R.id.searchField)


        clearBtn = findViewById(R.id.clearIcon)

        clearBtn.setOnClickListener {

            editText.setText("")
            input = ""
            tracks.clear()
            writeList(sharedPref) //сохранение списка в sharedPreferences
            loadList(sharedPref) //загрузка из sharedPreferences
            if (trackList.isNotEmpty()) {
                tracks.addAll(trackList.reversed()) //reversed для обеспечения первой позиции последней запрошенной записи
                findList.visibility = View.VISIBLE
                onTitleAndButton()
            }
            recyclerView.adapter?.notifyDataSetChanged()
            place_200.visibility = View.GONE
            place_500.visibility = View.GONE

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
                    offTitleAndButton()
                    searchDebounce()
                }

                clearBtn.visibility = clearButtonVisibility(s)
            }

            override fun afterTextChanged(s: Editable?) {

            }
        }
        editText.addTextChangedListener(simpleTextWatcher)

        recyclerView = findViewById(R.id.findList)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter

        updateButton.setOnClickListener {
            onFindToInternet()
        }

    }

    private fun searchDebounce(){
        progressBar.visibility = View.VISIBLE
        handlerFind.removeCallbacks(searchRunnable)
        handlerFind.postDelayed(searchRunnable, SEARCH_DELAY)
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

    private fun write(
        sharedPreferences: SharedPreferences,
        tracksList: List<Track>
    ) { // функция сохранения в sharedPreferences служебная
        val json = Gson().toJson(tracksList)
        sharedPreferences.edit()
            .putString(TRACK_LIST_KEY, json)
            .apply()
    }

    private fun onFindToInternet() {
        offTitleAndButton()
        if (!input.isNullOrEmpty()) {
            trackService.search(input.toString()).enqueue(object : Callback<TrackResponse> {
                override fun onResponse(
                    call: Call<TrackResponse>,
                    response: Response<TrackResponse>
                ) {
                    progressBar.visibility = View.GONE

                    if (response.code() == 200) {
                        tracks.clear()
                        val resp = response.body()?.results;
                        if (resp?.isNotEmpty() == true) {
                            if (place_500.visibility == View.VISIBLE) {
                                place_500.visibility = View.GONE
                            }
                            tracks.addAll(resp!!)
                            recyclerView.adapter?.notifyDataSetChanged()

                        }
                        if (tracks.isEmpty()) {
                            findList.visibility = View.GONE
                            place_200.visibility = View.VISIBLE
                        }else{
                            if(tracks.isEmpty() && trackList.isNotEmpty()){
                                tracks.addAll(trackList.reversed())
                                findList.visibility = View.VISIBLE
                                place_200.visibility = View.GONE
                            }else if(tracks.isNotEmpty()){
                                findList.visibility = View.VISIBLE
                                place_200.visibility = View.GONE
                            }else if(tracks.isEmpty() && trackList.isEmpty()){
                                findList.visibility = View.VISIBLE
                                place_200.visibility = View.GONE
                            }

                        }
                    } else {
                        val code = response.code()
                        val tmp = "" + R.string.error_of_server + " ${code}"
                        if(place_200.isVisible || findList.isVisible) {
                            place_500.visibility = View.VISIBLE
                            place_200.visibility = View.GONE
                            findList.visibility = View.GONE
                            titleFind.visibility = View.GONE // включение видимости заголовка "Вы искали"
                            clearButtonFind.visibility = View.GONE //кнопка "Очистить список" видимость true
                        }
                    }
                }

                override fun onFailure(call: Call<TrackResponse>, t: Throwable) {
                    progressBar.visibility = View.GONE
                    place_200.visibility = View.GONE
                    findList.visibility = View.GONE
                    place_500.visibility = View.VISIBLE
                    titleFind.visibility = View.GONE // включение видимости заголовка "Вы искали"
                    clearButtonFind.visibility = View.GONE //кнопка "Очистить список" видимость true
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



    override fun onClick(track: Track) { // обработчик нажатия на запись в списке треков
        val sharedPref =
            getSharedPreferences(TRACK_LIST_KEY, MODE_PRIVATE)//инициация SharedPreferences
        objectSave.onFindToTrack(track.trackId.toLong())
        currentTrack = track;
        val tmp = objectSave.trackTmp[0]
        objectSave.addTrackToList(tmp)


        val intent = Intent(this, PlayerActivity21::class.java)
        //val intent = Intent(this, PlayerActivity::class.java)
        if (track != null) {
            intent.putExtra("trackName", track.trackName)
            intent.putExtra("trackPicture", track.artworkUrl100)
            intent.putExtra("nameSinger", track.artistName)
            intent.putExtra("longTime", track.trackTimeMillis)
            intent.putExtra("album", track.primaryGenreName)
            intent.putExtra("url", track.previewUrl)
        }
        startActivity(intent)
        if (trackList.isNotEmpty()) {
            //tracks.clear()
            //tracks.addAll(trackList.reversed())
            writeList(sharedPref) //сохранение списка в sharedPreferences
            loadList(sharedPref) //загрузка из sharedPreferences
            recyclerView.adapter?.notifyDataSetChanged()
        }
    }
    private fun offTitleAndButton(){
        if(titleFind.isVisible){
            titleFind.visibility = View.GONE
            clearButtonFind.visibility = View.GONE
        }
    }
    private fun onTitleAndButton(){
        if(!titleFind.isVisible){
            titleFind.visibility = View.VISIBLE
            clearButtonFind.visibility = View.VISIBLE
        }
    }
}
