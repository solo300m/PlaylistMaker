package com.example.playlistmaker.find

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.R

class FindActivity : AppCompatActivity() {
    private lateinit var editText: EditText
    private var input: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find)

        val findButton = findViewById<ImageView>(R.id.backFindToMain)
        findButton.setOnClickListener {
            finish()
            //startActivity(Intent(this, MainActivity::class.java))
        }

        editText = findViewById<EditText>(R.id.searchField)
        // val linLayout = findViewById<LinearLayout>(R.id.editContainer)
        val clearBtn = findViewById<ImageView>(R.id.clearIcon)

        clearBtn.setOnClickListener {
            editText.setText("")
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

        val recyclerView = findViewById<RecyclerView>(R.id.findList)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        recyclerView.adapter = TrackAdapter(trackList)
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

    private companion object {
        const val TEXT_VIEW_KEY = "TEXT_VIEW_KEY"
        val trackList: MutableList<Track> = mutableListOf(
            Track(
                1, "Smells Like Teen Spirit",
                "Nirvana", "5:01",
                "https://is5-ssl.mzstatic.com/image/thumb/Music115/v4/7b/58/c2/7b58c21a-2b51-2bb2-e59a-9bb9b96ad8c3/00602567924166.rgb.jpg/100x100bb.jpg"
            ),
            Track(
                2, "Billie Jean",
                "Michael Jackson", "4:35",
                "https://is5-ssl.mzstatic.com/image/thumb/Music125/v4/3d/9d/38/3d9d3811-71f0-3a0e-1ada-3004e56ff852/827969428726.jpg/100x100bb.jpg"
            ),
            Track(
                3,
                "Stayin' Alive",
                "Bee Gees",
                "4:10",
                "https://is4-ssl.mzstatic.com/image/thumb/Music115/v4/1f/80/1f/1f801fc1-8c0f-ea3e-d3e5-387c6619619e/16UMGIM86640.rgb.jpg/100x100bb.jpg"
            ),
            Track(
                4,
                "Whole Lotta Love",
                "Led Zeppelin",
                "5:33",
                "https://is2-ssl.mzstatic.com/image/thumb/Music62/v4/7e/17/e3/7e17e33f-2efa-2a36-e916-7f808576cf6b/mzm.fyigqcbs.jpg/100x100bb.jpg"
            ),
            Track(
                5,
                "Sweet Child O'Mine",
                "Guns N' Roses",
                "5:03",
                "https://is5-ssl.mzstatic.com/image/thumb/Music125/v4/a0/4d/c4/a04dc484-03cc-02aa-fa82-5334fcb4bc16/18UMGIM24878.rgb.jpg/100x100bb.jpg"
            )
        )
    }
}
