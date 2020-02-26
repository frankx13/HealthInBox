package com.studio.neopanda.healthinbox.database

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(tableName = "aliments_table", indices = [Index(value = ["name"], unique = true)])
data class Aliment(var name: String, var calories: Int, var weight: Int) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
