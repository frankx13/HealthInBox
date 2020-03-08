package com.studio.neopanda.healthinbox.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.studio.neopanda.healthinbox.database.entities.Weight

@Dao
interface WeightDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(weight: Weight)

    @Update
    fun update(weight: Weight)

    @Delete
    fun delete(weight: Weight)

    @Query("DELETE FROM weight_table")
    fun deleteAllWeights()

    @get:Query("SELECT * FROM weight_table ORDER BY date DESC")
    val allWeights: LiveData<List<Weight>>

    @Query("SELECT * FROM weight_table WHERE id LIKE :searchID")
    fun searchWeightsByDate(searchID: Int?): List<Weight>
}