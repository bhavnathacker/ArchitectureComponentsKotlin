package com.training.architecturecomponentskotlin.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface WordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWord(word:Word)

    @Delete
    fun deleteWord(word:Word)

    @Query("DELETE FROM word_table")
    fun deleteAllWords()

    @Query("SELECT * FROM word_table ORDER BY name ASC")
    fun getAllWords(): LiveData<List<Word>>

    @Query("SELECT * FROM word_table WHERE name = :name")
    fun getWordByName(name:String): Word

}