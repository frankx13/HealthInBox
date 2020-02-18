package com.studio.neopanda.healthinbox.database

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface AlimentDAO {

    @Insert
    fun insert(aliments : Aliments)

    @Query("DELETE FROM aliments_tables")
    fun deleteAllAliments()

    @Query("SELECT * FROM aliments_tables ")
    fun getAllAliments(): LiveData<List<Aliments>>

}