package com.training.architecturecomponentskotlin.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.training.architecturecomponentskotlin.R
import com.training.architecturecomponentskotlin.utils.*

class NewWordActivity : AppCompatActivity() {

    private lateinit var mWordEditText: EditText
    private lateinit var mMeaningEditText: EditText
    private lateinit var mSaveBtn: Button
    private lateinit var mDeleteBtn: Button

    private lateinit var mWord: String
    private lateinit var mMeaning: String
    private var isNewWord :Boolean = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_word)

        mWordEditText = findViewById(R.id.word_text)
        mMeaningEditText = findViewById(R.id.meaning_text)
        mSaveBtn = findViewById(R.id.btn_save)
        mDeleteBtn = findViewById(R.id.btn_delete)

        var extras = intent.extras
        extras?.let {
            mWord = extras.get(EXTRA_KEY_WORD) as String
            mMeaning = extras.get(EXTRA_KEY_MEANING) as String
            mWordEditText.setText(mWord)
            mMeaningEditText.setText(mMeaning)
            mWordEditText.isEnabled = false
            isNewWord = false
        }

        if(isNewWord) {
            mDeleteBtn.visibility = View.GONE
        } else {
            mDeleteBtn.visibility = View.VISIBLE
        }


        mSaveBtn.setOnClickListener {
            val intent = Intent()
            if(TextUtils.isEmpty(mWordEditText.text) || TextUtils.isEmpty(mMeaningEditText.text)) {
                setResult(RESULT_ERROR, intent)
            } else {
                intent.putExtra(EXTRA_KEY_WORD, mWordEditText.text.toString())
                intent.putExtra(EXTRA_KEY_MEANING, mMeaningEditText.text.toString())
                setResult(RESULT_SAVE, intent)
            }
            finish()

        }

        mDeleteBtn.setOnClickListener {
            intent.putExtra(EXTRA_KEY_WORD, mWordEditText.text.toString())
            setResult(RESULT_DELETE, intent)
            finish()
        }
    }
}
