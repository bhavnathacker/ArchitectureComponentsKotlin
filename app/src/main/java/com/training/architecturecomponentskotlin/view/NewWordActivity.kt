package com.training.architecturecomponentskotlin.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.training.architecturecomponentskotlin.R
import com.training.architecturecomponentskotlin.utils.*
import kotlinx.android.synthetic.main.activity_new_word.*

class NewWordActivity : AppCompatActivity() {

    private lateinit var mWord: String
    private lateinit var mMeaning: String
    private var isNewWord :Boolean = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_word)


        var extras = intent.extras
        extras?.let {
            mWord = extras.get(EXTRA_KEY_WORD) as String
            mMeaning = extras.get(EXTRA_KEY_MEANING) as String
            word_text.setText(mWord)
            meaning_text.setText(mMeaning)
            word_text.isEnabled = false
            isNewWord = false
        }

        if(isNewWord) {
            btn_delete.visibility = View.GONE
        } else {
            btn_delete.visibility = View.VISIBLE
        }


        btn_save.setOnClickListener {
            val intent = Intent()
            if(TextUtils.isEmpty(word_text.text) || TextUtils.isEmpty(meaning_text.text)) {
                setResult(RESULT_ERROR, intent)
            } else {
                intent.putExtra(EXTRA_KEY_WORD, word_text.text.toString())
                intent.putExtra(EXTRA_KEY_MEANING, meaning_text.text.toString())
                setResult(RESULT_SAVE, intent)
            }
            finish()

        }

        btn_delete.setOnClickListener {
            intent.putExtra(EXTRA_KEY_WORD, word_text.text.toString())
            setResult(RESULT_DELETE, intent)
            finish()
        }
    }
}
