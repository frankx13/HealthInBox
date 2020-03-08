package com.studio.neopanda.healthinbox.database.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "weight_table", indices = [Index(value = ["date"], unique = true)])
data class Weight(var value: Int, var date: String) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}