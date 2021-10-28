package com.training.architecturecomponentskotlin.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.training.architecturecomponentskotlin.R
import com.training.architecturecomponentskotlin.model.Word
import com.training.architecturecomponentskotlin.utils.*
import com.training.architecturecomponentskotlin.viewmodel.WordViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), WordListAdapter.ItemClickListener {

    private val wordActivityLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            processResult(result.resultCode, result.data)
        }

    override fun onItemClick(view: View, position: Int) {
        val intent = Intent(this, NewWordActivity::class.java)
        intent.putExtra(EXTRA_KEY_WORD, mAdapter.getWords()[position].name)
        intent.putExtra(EXTRA_KEY_MEANING, mAdapter.getWords()[position].meaning)
        wordActivityLauncher.launch(intent)
    }

    private lateinit var mRecyclerView: androidx.recyclerview.widget.RecyclerView
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

        mWordViewModel = ViewModelProvider(this).get(WordViewModel::class.java)

        mWordViewModel.getAllWords().observe(this, Observer { words ->
            words?.let {
                mAdapter.setWords(it)
            }

        })

        fab.setOnClickListener {
            val intent = Intent(this, NewWordActivity::class.java)
            wordActivityLauncher.launch(intent)
        }
    }

    private fun processResult(
        resultCode: Int,
        data: Intent?
    ) {
        when (resultCode) {
            RESULT_SAVE -> {
                data?.let {
                    val word = Word(
                        it.getStringExtra(EXTRA_KEY_WORD)!!,
                        it.getStringExtra(EXTRA_KEY_MEANING)!!
                    )
                    mWordViewModel.insertWord(word)
                }
            }
            RESULT_DELETE -> {
                data?.let {
                    val word = mWordViewModel.getWordByName(it.getStringExtra(EXTRA_KEY_WORD)!!)
                    word?.let {
                        mWordViewModel.deleteWord(word)
                    }
                    Toast.makeText(this, getString(R.string.word_deleted), Toast.LENGTH_SHORT)
                        .show()
                }
            }
            RESULT_ERROR -> {
                Toast.makeText(this, getString(R.string.empty_word_not_saved), Toast.LENGTH_SHORT)
                    .show()

            }
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
            R.id.action_clear_list -> {
                clearListAction()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun clearListAction() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.app_name))
        builder.setMessage(getString(R.string.msg_clear_list))

        builder.setPositiveButton(getString(R.string.label_yes)) { _, _ ->
            mWordViewModel.deleteAllWords()
            Toast.makeText(this, getString(R.string.list_cleared), Toast.LENGTH_SHORT).show()
        }

        builder.setNegativeButton(getString(R.string.label_no)) { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }
}
