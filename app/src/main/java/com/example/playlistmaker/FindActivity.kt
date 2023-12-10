package com.example.playlistmaker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.DEBUG_PROPERTY_VALUE_AUTO

class FindActivity: AppCompatActivity() {
    lateinit var editText: EditText
    var input:String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find)

        val findButton = findViewById<ImageView>(R.id.backFindToMain)
        findButton.setOnClickListener {
            val intent4 = Intent(this, MainActivity::class.java)
            startActivity(intent4)
        }

        editText = findViewById<EditText>(R.id.searchField)
        val linLayout = findViewById<LinearLayout>(R.id.editContainer)
        val clearBtn = findViewById<ImageView>(R.id.clearIcon)

        clearBtn.setOnClickListener {
            editText.setText("")
            val view = this.currentFocus
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            inputMethodManager?.hideSoftInputFromWindow(view?.windowToken,0)
        }
        if(savedInstanceState != null){
            editText.setText(savedInstanceState.getString(TEXT_VIEW_KEY))
        }
        val simpleTextWatcher = object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!s.isNullOrEmpty()){
                    input = s.toString()
                }
                clearBtn.visibility = clearButtonVisibility(s)
            }

            override fun afterTextChanged(s: Editable?) {

            }
        }
        editText.addTextChangedListener(simpleTextWatcher)
    }
    private fun clearButtonVisibility(s: CharSequence?):Int{
        if(s.isNullOrEmpty()){
            return View.INVISIBLE
        }else{
            return View.VISIBLE
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val tmp = savedInstanceState.getString(TEXT_VIEW_KEY, " ")
        if (!tmp.isNullOrEmpty()){
            editText.setText(tmp.toString())
        }else{
            editText.setText("")
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState?.run{
            putString(TEXT_VIEW_KEY, editText.text.toString())
        }
    }
    companion object{
        var TEXT_VIEW_KEY = "TEXT_VIEW_KEY"
        var VALUE_STRING = ""
    }
}
