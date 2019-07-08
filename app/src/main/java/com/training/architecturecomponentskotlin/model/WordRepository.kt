package com.training.architecturecomponentskotlin.model

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask

class WordRepository(application: Application) {
    private val wordDao:WordDao
    private val allWords: LiveData<List<Word>>

    init {
        val db = WordRoomDatabase.getInstance(application)
        wordDao = db.wordDao()
        allWords = wordDao.getAllWords()
        //Test comment
    }

    fun insertWord(word:Word){
        InsertAsyncTask(wordDao).execute(word)
    }

    fun deleteWord(word:Word) {
        DeleteAsyncTask(wordDao).execute(word)
    }

    fun deleteAllWords() {
        DeleteAllAsyncTask(wordDao).execute()
    }

    fun getAllWords(): LiveData<List<Word>> {
        return allWords
    }

    fun getWordByName(name:String): Word? {
        val allWordsList = allWords.value?.toList()

        allWordsList?.iterator()?.forEach {
            if(it.name == name) {
                return it
            }
        }
        return null
    }

    private class InsertAsyncTask(private val dao: WordDao) :AsyncTask<Word, Void, Void>() {
        override fun doInBackground(vararg params: Word): Void? {
            dao.insertWord(params[0])
            return null
        }
    }

    private class DeleteAsyncTask(private val dao: WordDao) :AsyncTask<Word, Void, Void>() {
        override fun doInBackground(vararg params: Word): Void? {
            dao.deleteWord(params[0])
            return null
        }
    }

    private class DeleteAllAsyncTask(private val dao: WordDao) :AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg params: Void): Void? {
            dao.deleteAllWords()
            return null
        }
    }

}