package com.training.architecturecomponentskotlin.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.annotation.NonNull

@Entity(tableName = "word_table")
data class Word(@PrimaryKey @NonNull val name: String, @NonNull val meaning: String){
}