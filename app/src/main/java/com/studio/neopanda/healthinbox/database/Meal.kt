package com.studio.neopanda.healthinbox.database

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "meals_table", indices = [Index(value = ["name"], unique = true)])
data class Meal(var name: String, var date: String, val calories: Int) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
