package com.training.architecturecomponentskotlin.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.training.architecturecomponentskotlin.R
import com.training.architecturecomponentskotlin.model.Word
import com.training.architecturecomponentskotlin.utils.*
import com.training.architecturecomponentskotlin.viewmodel.WordViewModel

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), WordListAdapter.ItemClickListener {

    override fun onItemClick(view: View, position: Int) {
        val intent = Intent(this, NewWordActivity::class.java)
        intent.putExtra(EXTRA_KEY_WORD, mAdapter.getWords()[position].name)
        intent.putExtra(EXTRA_KEY_MEANING, mAdapter.getWords()[position].meaning)
        startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE)
    }

    private lateinit var mRecyclerView:RecyclerView
    private lateinit var mAdapter: WordListAdapter
    private var mWords: List<Word> = mutableListOf<Word>()
    private lateinit var mWordViewModel: WordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        mRecyclerView = findViewById(R.id.recyclerView)
        mAdapter = WordListAdapter(this, this)
        mAdapter.setWords(mWords)
        mRecyclerView.adapter = mAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(this)

        mWordViewModel = ViewModelProviders.of(this).get(WordViewModel::class.java)

        mWordViewModel.getAllWords().observe(this, Observer { words ->
            words?.let {
                mAdapter.setWords(it)
            }

        })

        fab.setOnClickListener { view ->
            val intent = Intent(this, NewWordActivity::class.java)
            startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_SAVE) {
            data?.let {
                val word = Word(it.getStringExtra(EXTRA_KEY_WORD), it.getStringExtra(EXTRA_KEY_MEANING))
                mWordViewModel.insertWord(word)
            }
        } else if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_ERROR) {
            Toast.makeText(this, getString(R.string.empty_word_not_saved), Toast.LENGTH_SHORT).show()

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
