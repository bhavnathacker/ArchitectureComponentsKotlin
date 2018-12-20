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
        var dbInstance: WordRoomDatabase? = null

        var dbName = "word_database"

        fun getInstance(context: Context): WordRoomDatabase {
            val tempInstance = dbInstance
            if(tempInstance != null) {
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext, WordRoomDatabase::class.java,dbName).build()
                dbInstance = instance
                return instance
            }

        }
    }
}