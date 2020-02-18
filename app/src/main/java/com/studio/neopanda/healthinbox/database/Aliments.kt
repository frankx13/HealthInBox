package com.studio.neopanda.healthinbox.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "aliments_tables")
data class Aliments(
    var name: String,
    var calories: Int

) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

}