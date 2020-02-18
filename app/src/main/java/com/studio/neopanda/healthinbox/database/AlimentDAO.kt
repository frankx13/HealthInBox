package com.studio.neopanda.healthinbox.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AlimentDao {

    @Insert
    fun insert(aliment: Aliment)

    @Update
    fun update(aliment: Aliment)

    @Delete
    fun delete(aliment: Aliment)

    @Query("DELETE FROM aliments_table")
    fun deleteAllAliments()

    @get:Query("SELECT * FROM aliments_table ORDER BY name DESC")
    val allAliments: LiveData<List<Aliment>>
}