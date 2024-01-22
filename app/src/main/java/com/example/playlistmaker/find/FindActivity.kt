package com.example.playlistmaker.find

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FindActivity : AppCompatActivity() {
    private lateinit var editText: EditText
    private lateinit var clearBtn:ImageView
    private lateinit var recyclerView:RecyclerView
    private lateinit var findButton:ImageView

    private val tracks = ArrayList<Track>()
    private val adapter = TrackAdapter(tracks)

    private var input: String? = null


    private val retrofit = Retrofit.Builder()
        .baseUrl(tracBaseURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val trackService = retrofit.create(IMDbApi::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find)

        findButton = findViewById(R.id.backFindToMain)
        findButton.setOnClickListener {
            finish()
        }

        editText = findViewById<EditText>(R.id.searchField)
        // val linLayout = findViewById<LinearLayout>(R.id.editContainer)


        clearBtn = findViewById(R.id.clearIcon)

        clearBtn.setOnClickListener {
            editText.setText("")
            tracks.clear()
            recyclerView.adapter?.notifyDataSetChanged()
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
        editText.setOnEditorActionListener{ _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_DONE){
                Log.d("MYAPPLOG","Завершен ввод в строку поиска. Введенное значение ${input}")
                Toast.makeText(applicationContext, "Выбран для поиска ${input}",Toast.LENGTH_LONG).show()
                onFindToInternet()
                true
            }
            false
        }
        recyclerView = findViewById(R.id.findList)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
    }

    private fun onFindToInternet(){
        if(!input.isNullOrEmpty()){
            trackService.search(input.toString()).enqueue(object : Callback<TrackResponse>{
                override fun onResponse(
                    call: Call<TrackResponse>,
                    response: Response<TrackResponse>
                ) {
                    if(response.code()==200){
                        tracks.clear()
                        if(response.body()?.results?.isNotEmpty() == true){
                            tracks.addAll(response.body()?.results!!)
                            recyclerView.adapter?.notifyDataSetChanged()
                        }
                        if(tracks.isEmpty()){
                            Toast.makeText(applicationContext,R.string.what_go_wrong,Toast.LENGTH_LONG).show()

                        }
                    }else{
                        val code = response.code()
                        val tmp = ""+R.string.error_of_server +" ${code}"
                        Toast.makeText(applicationContext,tmp,Toast.LENGTH_LONG).show()
                    }
                }
                override fun onFailure(call: Call<TrackResponse>, t: Throwable) {
                    Toast.makeText(applicationContext,R.string.to_wrong,Toast.LENGTH_LONG).show()
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

    private companion object {
        private const val tracBaseURL = "https://itunes.apple.com"
        private const val TEXT_VIEW_KEY = "TEXT_VIEW_KEY"
    }
}
