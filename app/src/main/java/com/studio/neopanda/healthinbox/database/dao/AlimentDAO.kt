package com.studio.neopanda.healthinbox.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.studio.neopanda.healthinbox.database.entities.Aliment

@Dao
interface AlimentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(aliment: Aliment)

    @Update
    fun update(aliment: Aliment)

    @Delete
    fun delete(aliment: Aliment)

    @Query("DELETE FROM aliments_table")
    fun deleteAllAliments()

    @get:Query("SELECT * FROM aliments_table ORDER BY name DESC")
    val allAliments: LiveData<List<Aliment>>

    @Query("SELECT * FROM aliments_table WHERE name LIKE :search")
    fun searchAliments(search: String?): List<Aliment>
}