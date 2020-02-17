package com.studio.neopanda.healthinbox.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Aliment(
    @PrimaryKey(autoGenerate = true)
    val uid: Int,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "calories") val calories: Int?
)