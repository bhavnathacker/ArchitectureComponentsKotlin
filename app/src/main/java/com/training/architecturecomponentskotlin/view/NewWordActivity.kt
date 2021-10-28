package com.training.architecturecomponentskotlin.view

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.training.architecturecomponentskotlin.databinding.ActivityNewWordBinding
import com.training.architecturecomponentskotlin.utils.*

class NewWordActivity : AppCompatActivity() {

    private lateinit var mWord: String
    private lateinit var mMeaning: String
    private var isNewWord: Boolean = true

    private lateinit var binding: ActivityNewWordBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewWordBinding.inflate(layoutInflater)
        setContentView(binding.root)


        var extras = intent.extras
        extras?.let {
            mWord = extras.get(EXTRA_KEY_WORD) as String
            mMeaning = extras.get(EXTRA_KEY_MEANING) as String
            binding.wordText.setText(mWord)
            binding.meaningText.setText(mMeaning)
            binding.wordText.isEnabled = false
            isNewWord = false
        }

        if (isNewWord) {
            binding.btnDelete.visibility = View.GONE
        } else {
            binding.btnDelete.visibility = View.VISIBLE
        }


        binding.btnSave.setOnClickListener {
            val intent = Intent()
            if (TextUtils.isEmpty(binding.wordText.text) || TextUtils.isEmpty(binding.meaningText.text)) {
                setResult(RESULT_ERROR, intent)
            } else {
                intent.putExtra(EXTRA_KEY_WORD, binding.wordText.text.toString())
                intent.putExtra(EXTRA_KEY_MEANING, binding.meaningText.text.toString())
                setResult(RESULT_SAVE, intent)
            }
            finish()

        }

        binding.btnDelete.setOnClickListener {
            intent.putExtra(EXTRA_KEY_WORD, binding.wordText.text.toString())
            setResult(RESULT_DELETE, intent)
            finish()
        }
    }
}
