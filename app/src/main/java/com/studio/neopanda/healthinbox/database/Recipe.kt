package com.studio.neopanda.healthinbox.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes_table")
data class Recipe(
    var name: String,
    var ingredients: String,
    var instructions: String,
    var peopleSize: Int,
    var difficulty: Int,
    var cost: Int,
    var time: Int
) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
