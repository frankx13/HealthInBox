package com.studio.neopanda.healthinbox.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AlimentDAO {
    @Query("SELECT * FROM aliment")
    fun getAll(): List<Aliment>

    @Query("SELECT * FROM aliment WHERE uid IN (:alimentIds)")
    fun loadAllByIds(alimentIds: IntArray): List<Aliment>

    @Query("SELECT * FROM aliment WHERE name LIKE :name LIMIT 1")
    fun findByName(name: String): Aliment

    @Insert
    fun insertAll(vararg aliments: Aliment)

    @Delete
    fun delete(aliments: Aliment)
}