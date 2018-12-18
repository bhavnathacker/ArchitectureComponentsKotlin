package com.training.architecturecomponentskotlin.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.training.architecturecomponentskotlin.model.Word
import com.training.architecturecomponentskotlin.model.WordRepository

class WordViewModel(application: Application): AndroidViewModel(application) {
    private val wordRepository:WordRepository
    private val allWords: LiveData<List<Word>>

    init {
        wordRepository = WordRepository(application)
        allWords = wordRepository.getAllWords()
    }

    fun insertWord(word:Word) {
        wordRepository.insertWord(word)
    }

    fun deleteWord(word:Word) {
        wordRepository.deleteWord(word)
    }

    fun deleteAllWords() {
        wordRepository.deleteAllWords()
    }

    fun getAllWords() = allWords

    fun getWordByName(name:String): Word? {
        return wordRepository.getWordByName(name)
    }
}