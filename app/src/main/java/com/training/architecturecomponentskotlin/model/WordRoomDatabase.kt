package com.training.architecturecomponentskotlin.model

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = [Word::class], version = 1)
abstract class WordRoomDatabase: RoomDatabase() {
    abstract fun wordDao(): WordDao

    companion object {
        @Volatile
        var database: WordRoomDatabase? = null

        fun getInstance(context: Context): WordRoomDatabase? {
            if(database == null) {
                synchronized(WordRoomDatabase::class.java) {
                    if(database == null) {
                        database = Room.databaseBuilder(context.applicationContext, WordRoomDatabase::class.java, "word_database").build()
                    }
                }
            }
            return database
        }
    }
}