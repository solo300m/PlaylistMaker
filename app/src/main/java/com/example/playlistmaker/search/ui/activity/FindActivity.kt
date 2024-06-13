package com.example.playlistmaker.search.ui.activity

import android.content.Context
import android.content.Intent
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
import com.example.playlistmaker.player.domain.models.Track
import com.example.playlistmaker.search.data.dto.ITunes
import com.example.playlistmaker.search.ui.utils.TrackAdapter
import com.example.playlistmaker.search.data.dto.TrackResponse
import com.example.playlistmaker.search.ui.utils.TrackViewHolder
import com.example.playlistmaker.player.ui.activity.PlayerActivity
import com.example.playlistmaker.search.ui.view_model.SearchViewModelImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FindActivity : AppCompatActivity(), TrackViewHolder.Listener {

    private lateinit var viewModelSearch: SearchViewModelImpl

    private lateinit var editText: EditText
    private lateinit var clearBtn: ImageView
    private lateinit var recyclerView: RecyclerView
    private lateinit var findButton: ImageView
    private lateinit var codeRequest200: LinearLayout
    private lateinit var codeRequest500: LinearLayout
    private lateinit var updateButton: Button
    private lateinit var titleFind: TextView//заголовок сохраненного списка
    private lateinit var clearButtonFind: Button//кнопка очистки списка сохраненных треков
    private lateinit var progressBar: ProgressBar // виджет ProgressBar
    private lateinit var adapter: TrackAdapter

    private var input: String? = null

    private lateinit var trackService: ITunes
    private val searchRunnable =
        Runnable { onFindToInternet() } // Runnable объект для debonce поисковой строки
    private val handlerFind = Handler(Looper.getMainLooper()) // Handler главного IU потока


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find)

        viewModelSearch = ViewModelProvider(
            this, SearchViewModelImpl.getViewModelFactory(
            )
        )[SearchViewModelImpl::class.java]

        trackService = viewModelSearch.getITunesClient()

        progressBar = findViewById(R.id.progressBar)
        titleFind = findViewById(R.id.titleFind)
        clearButtonFind = findViewById(R.id.clearFind)

        viewModelSearch.loadList() // загрузка сохраненного списка из sharedPreferences
        if (viewModelSearch.trackList.value?.isNotEmpty() == true) {
            viewModelSearch.tracks.value?.clear()
            viewModelSearch.trackList.value?.reversed().let {
                if (it != null) {
                    viewModelSearch.tracks.value?.addAll(it)
                }
            } // загрузка сохраненного списка из sharedPreferences в реверсивном виде
            onTitleAndButton()
        } else {
            offTitleAndButton()
        }
        adapter = viewModelSearch.tracks.value.let { TrackAdapter(it!!, this) }

        codeRequest200 = findViewById(R.id.placeholder_200)
        codeRequest500 = findViewById(R.id.placeholder_500)
        updateButton = findViewById(R.id.updateButton)
        findButton = findViewById(R.id.backFindToMain)
        recyclerView = findViewById(R.id.findList)
        findButton.setOnClickListener {
            finish()
        }
        clearButtonFind.setOnClickListener {//обработчик кнопки "Очистка списка"
            viewModelSearch.clearTrackList()
            offTitleAndButton()
            recyclerView.adapter?.notifyDataSetChanged()
        }
        editText = findViewById<EditText>(R.id.searchField)
        clearBtn = findViewById(R.id.clearIcon)
        clearBtn.setOnClickListener {
            cleanTextLine()
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
                    recyclerView.visibility = View.GONE
                    offTitleAndButton()
                    searchDebounce()
                }

                clearBtn.visibility = clearButtonVisibility(s)
            }

            override fun afterTextChanged(s: Editable?) {
            }
        }
        editText.addTextChangedListener(simpleTextWatcher)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
        updateButton.setOnClickListener {
            onFindToInternet()
        }
    }

    private fun cleanTextLine() {
        editText.setText("")
        input = ""
        viewModelSearch.tracks.value?.clear()
        viewModelSearch.writeList() //сохранение списка в sharedPreferences
        viewModelSearch.loadList() //загрузка из sharedPreferences
        if (viewModelSearch.trackList.value?.isNotEmpty() == true) {
            viewModelSearch.trackList.value?.reversed().let {
                if (it != null) {
                    if (it.isNotEmpty()) {
                        viewModelSearch.tracks.value?.addAll(it)
                    }
                }
            }
            //reversed для обеспечения первой позиции последней запрошенной записи
            recyclerView.visibility = View.VISIBLE
            onTitleAndButton()
        }
        recyclerView.adapter?.notifyDataSetChanged()
        codeRequest200.visibility = View.GONE
        codeRequest500.visibility = View.GONE

        val view = this.currentFocus
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    private fun searchDebounce() {
        progressBar.visibility = View.VISIBLE
        handlerFind.removeCallbacks(searchRunnable)
        handlerFind.postDelayed(searchRunnable, SEARCH_DELAY)
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
                        viewModelSearch.tracks.value?.clear()
                        val resp = response.body()?.results;
                        if (resp?.isNotEmpty() == true) {
                            if (codeRequest500.visibility == View.VISIBLE) {
                                codeRequest500.visibility = View.GONE
                            }
                            viewModelSearch.tracks.value?.addAll(resp)
                            recyclerView.visibility = View.VISIBLE
                            recyclerView.adapter?.notifyDataSetChanged()

                        }
                        if (viewModelSearch.tracks.value?.isEmpty() == true) {
                            recyclerView.visibility = View.GONE
                            codeRequest200.visibility = View.VISIBLE
                        } else {
                            if (viewModelSearch.tracks.value?.isEmpty() == true && viewModelSearch.trackList.value?.isNotEmpty() == true) {
                                viewModelSearch.trackList.value?.reversed().let {
                                    if (it != null) {
                                        viewModelSearch.tracks.value?.addAll(it)
                                    }
                                }
                                recyclerView.visibility = View.VISIBLE
                                codeRequest200.visibility = View.GONE
                            } else if (viewModelSearch.tracks.value?.isNotEmpty() == true) {
                                recyclerView.visibility = View.VISIBLE
                                codeRequest200.visibility = View.GONE
                            } else if (viewModelSearch.tracks.value?.isEmpty() == true && viewModelSearch.trackList.value?.isEmpty() == true) {
                                recyclerView.visibility = View.VISIBLE
                                codeRequest200.visibility = View.GONE
                            }

                        }
                    } else {
                        if (codeRequest200.isVisible || recyclerView.isVisible) {
                            codeRequest500.visibility = View.VISIBLE
                            codeRequest200.visibility = View.GONE
                            recyclerView.visibility = View.GONE
                            titleFind.visibility =
                                View.GONE // включение видимости заголовка "Вы искали"
                            clearButtonFind.visibility =
                                View.GONE //кнопка "Очистить список" видимость true
                        }
                    }
                }

                override fun onFailure(call: Call<TrackResponse>, t: Throwable) {
                    progressBar.visibility = View.GONE
                    codeRequest200.visibility = View.GONE
                    recyclerView.visibility = View.GONE
                    codeRequest500.visibility = View.VISIBLE
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
        outState.run {
            putString(TEXT_VIEW_KEY, editText.text.toString())
        }
    }

    override fun onClick(track: Track) { // обработчик нажатия на запись в списке треков
        /*val sharedPref =
            getSharedPreferences(TRACK_LIST_KEY, MODE_PRIVATE)*///инициация SharedPreferences
        viewModelSearch.onFindToTrack(track.trackId.toLong())
        currentTrack = track;
        val tmp = viewModelSearch.getTracksTmp()[0]
        viewModelSearch.addTrackToList(tmp)


        val intent = Intent(this, PlayerActivity::class.java)
        if (track != null) {
            intent.putExtra("trackId", track.trackId)
            intent.putExtra("trackName", track.trackName)
            intent.putExtra("trackPicture", track.artworkUrl100)
            intent.putExtra("nameSinger", track.artistName)
            intent.putExtra("longTime", track.trackTimeMillis)
            intent.putExtra("album", track.primaryGenreName)
            intent.putExtra("url", track.previewUrl)
            intent.putExtra("country", track.country)
            intent.putExtra("releaseDate", track.releaseDate)
            intent.putExtra("genreName", track.primaryGenreName)
        }
        startActivity(intent)
        if (viewModelSearch.trackList.value?.isNotEmpty() == true) {
            viewModelSearch.writeList() //сохранение списка в sharedPreferences
            viewModelSearch.loadList() //загрузка из sharedPreferences
            recyclerView.adapter?.notifyDataSetChanged()
        }
    }

    private fun offTitleAndButton() {
        if (titleFind.isVisible) {
            titleFind.visibility = View.GONE
            clearButtonFind.visibility = View.GONE
        }
    }

    private fun onTitleAndButton() {
        if (!titleFind.isVisible) {
            titleFind.visibility = View.VISIBLE
            clearButtonFind.visibility = View.VISIBLE
        }
    }

    companion object {
        private const val TEXT_VIEW_KEY = "TEXT_VIEW_KEY"
        var currentTrack: Track? = null
        private const val SEARCH_DELAY = 2000L
    }
}
